package com.tech.skill.normal.controller;

import jakarta.annotation.Resource;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaController {

    @Resource
    private KafkaTemplate kafkaTemplate;

    @GetMapping("kafka")
    public String send(@RequestBody String msg) {
        kafkaTemplate.send("mongo_sync_topic", msg);
        return "success";
    }

}
