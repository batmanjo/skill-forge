package com.tech.skill.normal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yanmiao.wu
 */
@SpringBootApplication(exclude = {
//        DataSourceAutoConfiguration.class,
//        SpringBootConfiguration.class
})
@RestController
public class SkillNormalApplication {

    public static void main(String[] args) {
        SpringApplication.run(SkillNormalApplication.class, args);
    }

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }

}
