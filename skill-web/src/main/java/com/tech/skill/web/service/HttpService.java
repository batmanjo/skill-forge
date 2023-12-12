package com.tech.skill.web.service;


import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.tech.skill.web.dto.CalculatedSumDTO;
import com.tech.skill.web.dto.TwoNums;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Flow;

/**
 * @author yanmiao.wu
 */
@Service
public class HttpService {


    /**
     * 练习使用Http等技术
     */
    public void queryMultiServer() {

    }

    public void doMultiPostRequest() {
        HttpClient httpClient = HttpClient.newHttpClient();

        // 构建POST请求的数据
//        Map<String, Object> requestData = new HashMap<>();
//        requestData.put("key1", "value1");
//        requestData.put("key2", "value2");
        CalculatedSumDTO calculatedSumDTO = new CalculatedSumDTO();
        List<TwoNums> twoNums = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            TwoNums twoNum = new TwoNums();
            twoNum.setNum1(RandomUtil.randomInt(10));
            twoNum.setNum1(RandomUtil.randomInt(10));
            twoNums.add(twoNum);
        }
        calculatedSumDTO.setNums(twoNums);
        calculatedSumDTO.setMax(10);

        // 将数据转换为JSON格式
        JSON parse = JSONUtil.parse(calculatedSumDTO);
        String str = JSONUtil.toJsonStr(parse);

        // 创建HttpRequest实例，使用POST方法，并设置请求体
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create("localhost:8081/calculatedSum"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(str))
                .build();

        try {
            // 发送请求并获取响应
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            // 输出响应状态码和响应体
            System.out.println("Response Code: " + response.statusCode());
            System.out.println("Response Body: " + response.body());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
