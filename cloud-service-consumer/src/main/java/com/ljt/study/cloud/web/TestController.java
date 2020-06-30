package com.ljt.study.cloud.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author LiJingTang
 * @date 2020-06-30 18:40
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private LoadBalancerClient balancerClient;
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/hello")
    public String hello() {
        ServiceInstance instance = balancerClient.choose("cloud-service-provider");
        String url = instance.getUri().toString() + "/test/hello";
        return restTemplate.getForObject(url, String.class);
    }

}
