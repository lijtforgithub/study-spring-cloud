package com.ljt.study.cloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LiJingTang
 * @date 2021-05-11 19:23
 */
//@RefreshScope
@RestController
@RequestMapping("/config")
public class ConfigController {

    @Value("${config-test:nacos}")
    private String configTest;

    @GetMapping("/test")
    public String test() {
        return configTest;
    }

}
