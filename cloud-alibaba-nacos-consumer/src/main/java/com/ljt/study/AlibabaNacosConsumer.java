package com.ljt.study;

import com.alibaba.cloud.nacos.registry.NacosServiceRegistryAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author LiJingTang
 * @date 2021-12-17 16:29
 */
@EnableFeignClients
@SpringBootApplication(exclude = NacosServiceRegistryAutoConfiguration.class)
@MapperScan(basePackages = "com.ljt.study.dao")
public class AlibabaNacosConsumer {

    public static void main(String[] args) {
        SpringApplication.run(AlibabaNacosConsumer.class);
    }



}
