package com.tech.skill.forge;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tech.skill.forge.entity.User;
import com.tech.skill.forge.service.db.UserSecureService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Date;
import java.util.List;

@SpringBootTest
class SkillForgeApplicationTests {

    @Autowired
    UserSecureService userSecureService;

    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    RedisTemplate redisTemplate;

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
    @Test
    void setAndGetRedisUser(){
        stringRedisTemplate.opsForValue().set("key1","hello");
        String key1 = stringRedisTemplate.opsForValue().get("key1");
        System.out.println(key1 + "world");

        User userByAccountId = userSecureService.getUserByAccountId("3");
        //如未配置redisTemplate,以下代码报错
        redisTemplate.opsForValue().set("user3", userByAccountId);
        User user = (User)redisTemplate.opsForValue().get("user3");
        System.out.println(user);

    }

}
