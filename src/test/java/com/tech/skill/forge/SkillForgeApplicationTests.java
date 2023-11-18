package com.tech.skill.forge;

import com.tech.skill.forge.entity.User;
import com.tech.skill.forge.service.db.UserSecureService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

@SpringBootTest
class SkillForgeApplicationTests {

    @Autowired
    UserSecureService userSecureService;

    @Test
    void addUser() {
        User user = new User();
        user.setAccountId("2");
        user.setNickname("kong");
        user.setPassword("123456");
        user.setCreateTime(new Date());
        userSecureService.save(user);
    }

    @Test
    void queryUser(){
        User userByAccountId = userSecureService.getUserByAccountId("3");
        Date date = new Date();
        Date startTime = new Date(date.getTime() - 1000 * 3600 * 24 * 10L);
        List<User> usersByTime = userSecureService.getUsersByTime(startTime, date);
        System.out.println(usersByTime);
        System.out.println(userByAccountId);
    }

}
