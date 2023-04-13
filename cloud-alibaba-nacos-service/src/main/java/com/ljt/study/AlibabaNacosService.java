package com.ljt.study;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author LiJingTang
 * @date 2020-07-03 10:23
 */
@SpringBootApplication
@MapperScan(basePackages = "com.ljt.study.dao")
public class AlibabaNacosService {

    public static void main(String[] args) {
        SpringApplication.run(AlibabaNacosService.class);
    }

}
