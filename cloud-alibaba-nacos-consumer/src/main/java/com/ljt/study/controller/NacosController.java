package com.ljt.study.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author LiJingTang
 * @date 2021-12-05 12:04
 */
@RestController
public class NacosController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/port")
    public String getServicePort() {
        return restTemplate.getForObject("http://alibaba-nacos/port", String.class);
    }

}
