package com.ljt.study.controller;

import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * @author LiJingTang
 * @date 2021-06-02 09:24
 */
@RestController
@RequestMapping("/self")
public class SelfController {

    @Value("${server.port}")
    private String port;
    @Value("${spring.application.name}")
    private String appName;

    @SneakyThrows
    @GetMapping("/port")
    public String getServicePort() {
        TimeUnit.SECONDS.sleep(10);
        return String.format("%s From Port: %s", appName, port);
    }

    @SneakyThrows
    @GetMapping("/resp")
    public void resp(HttpServletResponse response) {
        ClassPathResource resource = new ClassPathResource("application.yml");
        response.getOutputStream().write(IOUtils.toByteArray(resource.getInputStream()));
    }

    @SneakyThrows
    @GetMapping("/app/{str}")
    public String getAppName(@PathVariable String str) {
        TimeUnit.SECONDS.sleep(5);
        return appName + " - " + str;
    }

}
