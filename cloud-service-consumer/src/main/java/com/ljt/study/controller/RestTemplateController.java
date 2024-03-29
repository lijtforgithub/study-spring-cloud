package com.ljt.study.controller;

import com.ljt.study.api.ServiceApi;
import com.ljt.study.api.dto.UserDTO;
import com.ljt.study.hystrix.HystrixService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author LiJingTang
 * @date 2020-06-30 18:40
 */
@RestController
@RequestMapping("/rest-template")
public class RestTemplateController {

    @Autowired
    private LoadBalancerClient balancerClient;
    @Autowired
    private HystrixService hystrixService;
    @LoadBalanced
    @Autowired
    private RestTemplate lbRestTemplate;
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/port")
    public String getPort() {
        // 负载均衡
        ServiceInstance instance = balancerClient.choose(ServiceApi.APP_NAME);
        String url = instance.getUri().toString() + "/api/port";
        // 没有被代理的RestTemplate只能根据IP地址
        return restTemplate.getForObject(url, String.class);
    }

    @GetMapping("/lb")
    public String getPortWithLb() {
        return hystrixService.getPort();
    }

    @GetMapping("/hystrix")
    public String getPortWithHystrix() {
        return hystrixService.getPortWithHystrix();
    }

    @GetMapping("/user")
    public UserDTO getUser(String token) {
        UserDTO userDTO = UserDTO.builder().id(1).name("Hello").build();
        return lbRestTemplate.postForObject("http://" + ServiceApi.APP_NAME + "/api/user" + (StringUtils.isNotBlank(token) ? "?token=" + token : ""), userDTO, UserDTO.class);
    }

}
