package com.ljt.study.cloud.web;

import com.ljt.study.cloud.hystrix.RestTemplateHystrix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import static com.ljt.study.cloud.util.Constant.PROVIDER;

/**
 * @author LiJingTang
 * @date 2020-06-30 18:40
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private LoadBalancerClient balancerClient;
    @Autowired
    private RestTemplateHystrix restTemplateHystrix;

    @GetMapping("/hello")
    public String hello() {
        ServiceInstance instance = balancerClient.choose(PROVIDER);
        String url = instance.getUri().toString() + "/test/hello";
        return new RestTemplate().getForObject(url, String.class);
    }

    @GetMapping("/hystrix")
    public String helloHystrix() {
        return restTemplateHystrix.hello();
    }

}
