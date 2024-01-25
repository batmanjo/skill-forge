package com.tech.skill.normal.repo;

import com.tech.skill.normal.entity.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author yanmiao.wu
 * @create 2024-01-25 17:54
 */
@Repository
public interface MovieRepo extends MongoRepository<Movie, String> {

}