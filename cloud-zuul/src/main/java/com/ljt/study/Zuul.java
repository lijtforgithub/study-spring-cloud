package com.ljt.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * 网关 隧道模式
 *
 * @author LiJingTang
 * @date 2020-07-03 10:23
 */
@EnableZuulProxy
@SpringBootApplication(exclude = RedisAutoConfiguration.class)
public class Zuul {

    public static void main(String[] args) {
        SpringApplication.run(Zuul.class);
    }

}
