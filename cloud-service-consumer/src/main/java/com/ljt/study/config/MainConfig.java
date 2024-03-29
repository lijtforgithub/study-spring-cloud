package com.ljt.study.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.ContentCachingResponseWrapper;

/**
 * @see ContentCachingResponseWrapper
 * @author LiJingTang
 * @date 2021-05-29 19:11
 */
@Slf4j
@Configuration
public class MainConfig {

    /**
     * RestTemplate 加上了注解 @LoadBalanced 只能根据实例Id去请求 不能根据IP地址
     * ribbon 默认规则是区域轮询 com.netflix.loadbalancer.ZoneAvoidanceRule
     */
    @LoadBalanced
    @Bean
    RestTemplate lbRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

//    @Bean
//    IRule rule() {
//        return new RandomRule();
//    }

}
