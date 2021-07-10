package com.ljt.study.web;

import com.ljt.study.client.ServiceFeignClient;
import com.ljt.study.config.FeignClientRequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

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
        return serviceFeignClient.getServicePort();
    }

}
