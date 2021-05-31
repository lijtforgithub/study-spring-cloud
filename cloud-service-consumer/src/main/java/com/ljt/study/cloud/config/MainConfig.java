package com.ljt.study.cloud.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

/**
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
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        final RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(Collections.singletonList((HttpRequest request, byte[] body, ClientHttpRequestExecution execution) -> {
            final ClientHttpResponse response = execution.execute(request, body);
            log.info("rest拦截： {} => {}", request.getURI().toString(), response.getRawStatusCode());
            return response;
        }));
        return restTemplate;
    }

//    @Bean
//    public IRule rule() {
//        return new RandomRule();
//    }

}
