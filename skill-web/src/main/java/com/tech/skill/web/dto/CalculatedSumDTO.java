package com.tech.skill.web.dto;

import lombok.Data;

import java.util.List;

/**
 * @author yanmiao.wu
 * @create 2023-12-12 17:43
 */
@Data
public class CalculatedSumDTO {
    List<TwoNums> Nums;
    Integer max;
}
