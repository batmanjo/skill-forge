package com.tech.skill.forge;

import com.tech.skill.forge.entity.User;
import com.tech.skill.forge.service.db.UserSecureService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class SkillForgeApplicationTests {

    @Autowired
    UserSecureService userSecureService;

    @Test
    void contextLoads() {
        User user = new User();
        user.setAccountId("3");
        user.setNickname("kong");
        user.setPassword("123456");
        user.setCreateTime(new Date());
        userSecureService.save(user);
    }

}
