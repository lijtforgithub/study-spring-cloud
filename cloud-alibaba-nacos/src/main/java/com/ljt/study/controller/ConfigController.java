package com.ljt.study.controller;

import com.ljt.study.prop.ConfigTestProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.context.scope.refresh.RefreshScopeRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LiJingTang
 * @date 2021-05-11 19:23
 */
@Slf4j
@RefreshScope
@RestController
@RequestMapping("/config")
public class ConfigController {

    @Value("${config.info:nacos}")
    private String configInfo;
    @Autowired
    private ConfigTestProperties configTestProperties;

    @GetMapping("/info")
    public String info() {
        return configInfo;
    }

    @GetMapping("/test")
    public ConfigTestProperties test() {
        return configTestProperties;
    }

    @EventListener
    public void configRefresh(RefreshScopeRefreshedEvent event) {
        log.info("配置项发生变化：{}", event.getName());
    }

}
