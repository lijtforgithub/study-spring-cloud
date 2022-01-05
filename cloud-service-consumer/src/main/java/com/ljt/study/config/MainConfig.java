package com.ljt.study.config;

import com.ljt.study.rest.RestConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.ContentCachingResponseWrapper;

/**
 * @see ContentCachingResponseWrapper
 * @author LiJingTang
 * @date 2021-05-29 19:11
 */
@Slf4j
@Import(RestConfig.class)
@Configuration
public class MainConfig {

    /**
     * RestTemplate 加上了注解 @LoadBalanced 只能根据实例Id去请求 不能根据IP地址
     * ribbon 默认规则是区域轮询 com.netflix.loadbalancer.ZoneAvoidanceRule
     */
    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

//    @Bean
//    public IRule rule() {
//        return new RandomRule();
//    }

}
