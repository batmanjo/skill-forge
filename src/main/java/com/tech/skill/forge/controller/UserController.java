package com.tech.skill.forge.controller;

import com.tech.skill.forge.entity.User;
import com.tech.skill.forge.service.db.UserSecureService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author yanmiao.wu
 * @create 2023-11-17 14:44
 */

@RestController
public class UserController {

    @Resource
    UserSecureService userSecureService;

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

}
