package com.ljt.study.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LiJingTang
 * @date 2021-06-02 09:24
 */
@RestController
@RequestMapping("/self")
public class SelfController {

    @Value("${server.port}")
    private String port;
    @Value("${spring.application.name}")
    private String appName;

    @GetMapping("/port")
    public String getServicePort() {
        return String.format("%s From Port: %s", appName, port);
    }

}
