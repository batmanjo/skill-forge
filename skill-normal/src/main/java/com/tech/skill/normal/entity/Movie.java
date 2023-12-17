package com.tech.skill.normal.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.annotation.Documented;

@Data
@Document("movie")
public class Movie {
    private String name;
}
