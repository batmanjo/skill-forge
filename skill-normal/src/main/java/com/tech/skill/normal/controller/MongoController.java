package com.tech.skill.normal.controller;

import com.tech.skill.normal.common.ResponseVO;
import com.tech.skill.normal.entity.Movie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class MongoController {

    @Autowired
    MongoTemplate mongoTemplate;

    @GetMapping("/mongo")
    public ResponseVO mongo(){
        List<Movie> all = mongoTemplate.findAll(Movie.class);
        all.forEach( movie -> log.info("movie is {}",movie)
        );
        return ResponseVO.success();
    }
}
