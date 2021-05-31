package com.ljt.study.cloud.web;

import com.ljt.study.cloud.api.ServiceApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @author LiJingTang
 * @date 2020-07-01 10:38
 */
@RestController
public class ApiController implements ServiceApi {

    @Value("${server.port}")
    private String port;
    @Value("${spring.application.name}")
    private String appName;

    @Override
    public String getCurrentTime() {
//        int i = 1 / 0;
        return LocalDateTime.now().toString();
    }

    @Override
    public String getServicePort() {
        return String.format("%s From Port: %s", appName, port);
    }

}
