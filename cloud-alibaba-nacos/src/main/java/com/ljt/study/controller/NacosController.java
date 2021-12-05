package com.ljt.study.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LiJingTang
 * @date 2021-12-05 12:04
 */
@RestController
public class NacosController {

    @Value("${server.port}")
    private String port;
    @Value("${spring.application.name}")
    private String appName;

    @GetMapping("/port")
    public String getServicePort() {
        return String.format("%s From Port: %s", appName, port);
    }

}
