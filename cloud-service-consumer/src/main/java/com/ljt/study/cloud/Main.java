package com.ljt.study.cloud;

import com.ljt.study.cloud.interceptor.LogClientHttpRequestInterceptor;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

/**
 * 服务消费
 *
 * @author LiJingTang
 * @date 2020-06-30 18:34
 */
@EnableHystrix
@EnableHystrixDashboard
@EnableFeignClients
@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    /**
     * RestTemplate 加上了注解 @LoadBalanced 只能根据实例Id去请求 不能根据IP地址
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        final RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(Collections.singletonList(new LogClientHttpRequestInterceptor()));
        return restTemplate;
    }

    @Bean
    public IRule rule() {
        return new RandomRule();
    }

}
