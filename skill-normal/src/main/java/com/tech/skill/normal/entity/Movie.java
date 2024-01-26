package com.tech.skill.normal.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;


/**
 * @author yanmiao.wu
 */
@Data
@Document("movie")
public class Movie {
    private String name;
    private Integer year;
}
