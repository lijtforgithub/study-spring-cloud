package com.ljt.study.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LiJingTang
 * @date 2020-07-05 10:14
 */
@RestController
@SpringBootApplication
public class SecurityServer {

    public static void main(String[] args) {
        SpringApplication.run(SecurityServer.class);
    }

    @GetMapping("/")
    public String index() {
        return "欢迎您";
    }

}
