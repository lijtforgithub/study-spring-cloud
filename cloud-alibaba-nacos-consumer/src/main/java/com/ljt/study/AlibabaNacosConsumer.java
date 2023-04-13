package com.ljt.study;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author LiJingTang
 * @date 2021-12-17 16:29
 */
@EnableFeignClients
@SpringBootApplication
@MapperScan(basePackages = "com.ljt.study.dao")
public class AlibabaNacosConsumer {

    public static void main(String[] args) {
        SpringApplication.run(AlibabaNacosConsumer.class);
    }

    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
