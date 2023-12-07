package com.tech.skill.web.service;

import com.tech.skill.web.feign.UserClient;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class CommonService {

    @Resource
    private UserClient userClient;

    public String internalRegister(String accountId){
        return userClient.insertUser(accountId);
    }
}
