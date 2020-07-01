package com.ljt.study.cloud.hystrix;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static com.ljt.study.cloud.util.Constant.PROVIDER;

/**
 * RestTemplate 方式使用 Hystrix 必须在配置类添加注解 @EnableCircuitBreaker或@EnableHystrix
 *
 * @author LiJingTang
 * @date 2020-07-01 19:37
 */
@Service
public class RestTemplateHystrix {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "helloError")
    public String hello() {
        return restTemplate.getForObject("http://" + PROVIDER + "/test/hello", String.class);
    }

    public String helloError() {
        return "Hello RestTemplateHystrix" + ", Sorry, ERROR!";
    }

}
