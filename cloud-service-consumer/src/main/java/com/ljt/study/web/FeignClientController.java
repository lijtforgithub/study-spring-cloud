package com.ljt.study.web;

import com.ljt.study.client.ServiceFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LiJingTang
 * @date 2020-07-01 10:48
 */
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
        return serviceFeignClient.getServicePort();
    }

}
