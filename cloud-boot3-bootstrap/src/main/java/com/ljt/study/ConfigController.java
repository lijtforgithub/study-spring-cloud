package com.ljt.study;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LiJingTang
 * @date 2025-09-05 16:53
 */
@RefreshScope
@RestController
@RequestMapping("/config")
public class ConfigController {

    @Value("${test.key}")
    private String key;

    @Resource
    private ConfigProperties configProperties;


    @GetMapping("/key")
    String getKey() {
        return key;
    }

    @GetMapping("/config")
    ConfigProperties getConfig() {
        return configProperties;
    }


}
