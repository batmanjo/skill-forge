package com.tech.skill.normal.controller;

import cn.hutool.core.util.RandomUtil;
import com.tech.skill.normal.common.ResponseVO;
import com.tech.skill.normal.entity.Movie;
import com.tech.skill.normal.repo.MovieRepo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("/mongo")
public class MongoController {

    @Autowired
    private MongoTemplate mongoTemplate;
    @Resource
    private MovieRepo movieRepo;

    @GetMapping("")
    public ResponseVO mongo() {
        List<Movie> all = mongoTemplate.findAll(Movie.class);
        all.forEach(movie -> log.info("movie is {}", movie)
        );
        return ResponseVO.success();
    }

    @GetMapping("/find")
    public ResponseVO mongo(@RequestParam("name") String movieName) {
        //这样太臃肿了
        Movie movie = mongoTemplate.findOne(new Query(Criteria.where("name").is(movieName)), Movie.class);
        //这样也很臃肿
        Movie movie1 = new Movie();
        movie1.setName(movieName);
        Example<Movie> example = Example.of(movie1);
        Optional<Movie> one = movieRepo.findOne(example);
        return ResponseVO.success(one);
    }

    @GetMapping("save")
    public ResponseVO mongoSave(@RequestParam("name") String movieName) {
        Movie movie = new Movie();
        movie.setName(movieName);
        movie.setYear(RandomUtil.randomInt(2000, 2100));
        Movie movie1 = mongoTemplate.save(movie);
        return ResponseVO.success(movie1);
    }


}
