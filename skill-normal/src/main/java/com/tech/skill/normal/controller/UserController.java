package com.tech.skill.normal.controller;

import cn.hutool.core.util.RandomUtil;
import com.tech.skill.normal.common.ResponseVO;
import com.tech.skill.normal.entity.User;
import com.tech.skill.normal.service.db.UserSecureService;
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
    public ResponseVO register(@RequestParam("id") String accountId) {
        User user = new User();
        user.setAccountId(accountId);
        user.setNickname("king");
        user.setPassword("123456");
        user.setCreateTime(new Date());
        userSecureService.save(user);
        return ResponseVO.success(user);
    }

    @GetMapping("/redis")
    public ResponseVO testRedis() {
        String s = RandomUtil.randomString(10);
        redisTemplate.opsForValue().set(s,s,20, TimeUnit.SECONDS);
        return ResponseVO.success(s);
    }

}
