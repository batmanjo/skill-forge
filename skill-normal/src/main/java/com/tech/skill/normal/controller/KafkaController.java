package com.tech.skill.normal.controller;

import jakarta.annotation.Resource;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaController {

    @Resource
    private KafkaTemplate kafkaTemplate;

    @GetMapping("kafka")
    public String send(@RequestParam(name = "msg") String msg) {
        kafkaTemplate.send("test", msg);
        return "success";
    }

}
