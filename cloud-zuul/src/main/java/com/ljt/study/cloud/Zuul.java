package com.ljt.study.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @author LiJingTang
 * @date 2020-07-03 10:23
 */
@EnableZuulProxy
@SpringBootApplication
public class Zuul {

    public static void main(String[] args) {
        SpringApplication.run(Zuul.class);
    }

}
