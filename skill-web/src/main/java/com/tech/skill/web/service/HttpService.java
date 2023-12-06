package com.tech.skill.web.service;


import org.springframework.stereotype.Service;

import java.net.http.HttpClient;

@Service
public class HttpService {


    /**
     * 练习使用Http等技术
     */
    public void queryMultiServer() {
        HttpClient httpClient = HttpClient.newBuilder().build();
    }

}
