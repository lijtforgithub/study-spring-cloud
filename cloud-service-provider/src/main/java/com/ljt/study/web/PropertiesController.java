package com.ljt.study.web;

import com.ljt.study.properties.TestProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.context.scope.refresh.RefreshScopeRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LiJingTang
 * @date 2021-06-07 15:13
 */
@Slf4j
@RefreshScope
@RestController
@RequestMapping("/prop")
public class PropertiesController {

    @Autowired
    private TestProperties testProperties;

    @GetMapping("/test")
    public TestProperties test() {
        return testProperties;
    }

    @EventListener
    public void configRefresh(RefreshScopeRefreshedEvent event) {
        log.info("配置项发生变化：{}", testProperties.toString());
    }

}
