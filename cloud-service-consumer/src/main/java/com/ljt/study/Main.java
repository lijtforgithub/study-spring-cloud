package com.ljt.study;

import com.ljt.study.api.ServiceApi;
import com.ljt.study.gray.GreyConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 服务消费
 *
 * @author LiJingTang
 * @date 2020-06-30 18:34
 */

/**
 * 指定服务
 */
@RibbonClient(value = ServiceApi.APP_NAME, configuration = GreyConfig.class)

@EnableHystrix
@EnableHystrixDashboard

@EnableFeignClients
@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}
