package com.ljt.study.controller;

import com.ljt.study.api.dto.UserDTO;
import com.ljt.study.client.ServiceFeignClient;
import com.ljt.study.config.ContextHolder;
import com.ljt.study.config.FeignClientRequestInterceptor;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;

/**
 * @author LiJingTang
 * @date 2020-07-01 10:48
 */
@Slf4j
@RestController
@RequestMapping("/feign")
public class FeignClientController {

    @Autowired
    private ServiceFeignClient serviceFeignClient;

    @GetMapping("/time")
    public String getTime() {
        return serviceFeignClient.getCurrentTime();
    }

    @GetMapping("/port")
    public String getPort() {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            log.info("token = {}", request.getHeader(FeignClientRequestInterceptor.TOKEN));
        }

        ContextHolder.THREAD_LOCAL.set("hello");
        HystrixRequestContext.initializeContext();
        ContextHolder.HYSTRIX_VARIABLE.set("world");

        return serviceFeignClient.getServicePort();
    }

    @GetMapping("/user")
    public UserDTO getUser() {
        return serviceFeignClient.getUser();
    }

    @SneakyThrows
    @GetMapping("/byte")
    public byte[] byteArray() {
        return serviceFeignClient.byteArray();
    }

    @SneakyThrows
    @GetMapping("/resp")
    public void resp(HttpServletResponse response) {
        serviceFeignClient.resp();
    }

    @SneakyThrows
    @GetMapping("/output")
    public InputStream output() {
        return serviceFeignClient.output();
    }

    @SneakyThrows
    @GetMapping("/input")
    public void input(InputStream input) {
        ClassPathResource resource = new ClassPathResource("application.yml");
        serviceFeignClient.input(resource.getInputStream());
    }

}
