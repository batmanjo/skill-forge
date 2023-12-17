package com.tech.skill.web.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("skill-normal")
public interface UserClient {


    @GetMapping("/normal/register")
    String insertUser(@RequestParam("id") String accountId);
}
