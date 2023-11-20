package com.tech.skill.forge.controller;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.RandomUtil;
import com.tech.skill.forge.entity.User;
import com.tech.skill.forge.service.db.UserSecureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author yanmiao.wu
 * @create 2023-11-17 14:44
 */

@RestController
public class UserController {

    @Resource
    UserSecureService userSecureService;

    @Autowired
    RedisTemplate redisTemplate;

    @GetMapping("/register")
    public String register(@RequestParam("id") String accountId) {
        User user = new User();
        user.setAccountId(accountId);
        user.setNickname("king");
        user.setPassword("123456");
        user.setCreateTime(new Date());
        userSecureService.save(user);
        return "";
    }

    @GetMapping("/redis")
    public String testRedis() {
        String s = RandomUtil.randomString(10);
        redisTemplate.opsForValue().set(s,s,20, TimeUnit.SECONDS);
        return "";
    }

}
