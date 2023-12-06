package com.tech.skill.normal.service.db;

import com.tech.skill.normal.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author yanmiao.wu
 * @create 2023-12-06 17:31
 */
@SpringBootTest
class UserSecureServiceTest {

    @Autowired
    private UserSecureService userSecureService;

    @Test
    void getUserByAccountId() {
        System.out.println(userSecureService.getUserByAccountId("123"));
    }

    @Test
    void getUsersByTime() {
        List<User> usersByTime = userSecureService.getUsersByTime(new Date(System.currentTimeMillis() - 1000L * 3600 * 24 * 30), new Date());
        usersByTime.stream().forEach(System.out::println);
    }
}