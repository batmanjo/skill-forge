package com.tech.skill.web.service;


import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.tech.skill.web.dto.CalculatedSumDTO;
import com.tech.skill.web.dto.TwoNums;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.*;

/**
 * @author yanmiao.wu
 */
@Slf4j
@Service
public class HttpService {

    public static final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 10, 60L, TimeUnit.SECONDS, new LinkedBlockingDeque<>());

    public static void main(String[] args) throws InterruptedException {

//        for (int i = 0; i < 10; i++) {
//            threadPoolExecutor.execute(() -> doMultiPostRequest());
//        }
//        // wait for the thread to finish executing
//        Thread.sleep(1000);

        int concurrentExecutions = 10;

        // 创建一个CompletableFuture数组，用于存储每次异步操作的CompletableFuture
        CompletableFuture<Void>[] futures = new CompletableFuture[concurrentExecutions];

        // 并发执行异步操作
        for (int i = 0; i < concurrentExecutions; i++) {
            futures[i] = asyncDoMultiPostRequest();
        }

        // 使用CompletableFuture.allOf等待所有异步操作完成
        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures);
        log.info("view current time : {}", System.currentTimeMillis());
        allOf.thenRun(() -> {
                    log.info("ensure all execution is complete, currentTime:{}", System.currentTimeMillis());
                    threadPoolExecutor.shutdown();
                }
        );

    }


    /**
     * 练习使用Http等技术
     */
    public void queryMultiServer() {

    }

    public static void doMultiPostRequest() {
        HttpClient httpClient = HttpClient.newHttpClient();

        CalculatedSumDTO calculatedSumDTO = new CalculatedSumDTO();
        List<TwoNums> twoNums = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            TwoNums twoNum = new TwoNums();
            twoNum.setNum1(RandomUtil.randomInt(10));
            twoNum.setNum2(RandomUtil.randomInt(10));
            twoNums.add(twoNum);
        }
        calculatedSumDTO.setNums(twoNums);
        calculatedSumDTO.setMax(10);

        // 将数据转换为JSON格式
        JSON parse = JSONUtil.parse(calculatedSumDTO);
        String str = JSONUtil.toJsonStr(parse);
        System.out.println(str);
        // 创建HttpRequest实例，使用POST方法，并设置请求体
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://127.0.0.1:8081/calculatedSum"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(str))
                .build();

        try {
            // 发送请求并获取响应
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            // 输出响应状态码和响应体
            log.info("Response Code: " + response.statusCode());
            log.info("Response Body: " + response.body());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static CompletableFuture<Void> asyncDoMultiPostRequest() {
        return CompletableFuture.runAsync(() -> {
            log.info("task start,current time = {}", System.currentTimeMillis());
            doMultiPostRequest();
            log.info("task end,current time = {}", System.currentTimeMillis());
        }, threadPoolExecutor);

    }
}
