package com.tech.skill.web.controller;

import com.tech.skill.web.dto.CalculatedSumDTO;
import com.tech.skill.web.service.CommonService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author yanmiao.wu
 */
@Slf4j
@RestController
public class CommonController {

    @Resource
    private CommonService commonService;

    @GetMapping("/internalRegister")
    public String internalRegister(@RequestParam("accountId") String accountId) {
        return commonService.internalRegister(accountId);
    }

    @PostMapping("/calculatedSum")
    public String calculatedSum(@RequestBody CalculatedSumDTO calculatedSumDTO) {
        log.info("calculatedSum calculatedSumDTO:{}", calculatedSumDTO);
        return commonService.calculatedSum(calculatedSumDTO);
    }

}
