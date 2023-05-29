package com.ljt.study.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

/**
 * @author LiJingTang
 * @date 2023-05-19 19:00
 */
@Configuration
class MainConfig {

    @Bean
    @Primary
//    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
