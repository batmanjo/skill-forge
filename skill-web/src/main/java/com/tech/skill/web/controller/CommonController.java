package com.tech.skill.web.controller;

import com.tech.skill.web.service.CommonService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yanmiao.wu
 */
@RestController
public class CommonController {

    @Resource
    private CommonService commonService;
    @GetMapping("/internalRegister")
    public String internalRegister(@RequestParam("accountId") String accountId){
        return commonService.internalRegister(accountId);
    }

}
