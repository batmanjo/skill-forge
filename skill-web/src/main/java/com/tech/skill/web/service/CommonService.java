package com.tech.skill.web.service;

import com.tech.skill.web.dto.CalculatedSumDTO;
import com.tech.skill.web.feign.UserClient;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class CommonService {

    @Resource
    private UserClient userClient;

    public String internalRegister(String accountId) {
        return userClient.insertUser(accountId);
    }

    public String calculatedSum(CalculatedSumDTO dto) {
        return dto.getNums().stream().map(
                twoNum -> {
                    return twoNum.getNum1() + twoNum.getNum2();
                }
        ).filter(i -> i > dto.getMax()).toList().toString();
    }
}
