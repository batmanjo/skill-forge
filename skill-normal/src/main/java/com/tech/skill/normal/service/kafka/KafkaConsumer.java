package com.tech.skill.normal.service.kafka;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
public class KafkaConsumer {

    @Resource
    private KafkaTemplate kafkaTemplate;


    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(groupId = "mongo-cdc", topics = ("mongo-cdc.data_transfer.user"))
    public void handMessage(ConsumerRecord consumerRecord) {
        try {
            System.out.println("进入kafkaConsumer");
            System.out.println("Received message: " + consumerRecord.value());

            // 解析收到的消息
            JsonNode rootNode = objectMapper.readTree((JsonParser) consumerRecord.value());
            JsonNode afterNode = rootNode.path("payload").path("after");

            if (!afterNode.isMissingNode()) {
                // 将after字段的内容转换为JSON字符串
                String afterJson = afterNode.asText();

                // 发送到mongo_sync_topic
                kafkaTemplate.send("mongo_sync_topic", afterJson);
                System.out.println("Message sent to mongo_sync_topic: " + afterJson);
            } else {
                System.out.println("No 'after' field found in the message.");
            }
        } catch (IOException e) {
            System.err.println("Error processing message: " + e.getMessage());
            e.printStackTrace();
        }

    }

//    @KafkaListener(groupId = "mongo-cdc", topics = ("mongo_sync_topic"))
//    public void handMessage2(ConsumerRecord consumerRecord) {
//            System.out.println("进入kafkaConsumer");
//            System.out.println("Received message: " + consumerRecord.value());
//    }
}
