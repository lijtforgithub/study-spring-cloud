package com.ljt.study.hystrix;

import com.ljt.study.api.ServiceApi;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

    @HystrixCommand(fallbackMethod = "portError")
    public String getPortWithHystrix() {
        return getPort();
    }

    public String portError() {
        return "Hello RestTemplateHystrix, Sorry, ERROR!";
    }

    public String getPort() {
        return restTemplate.getForObject("http://" + ServiceApi.APP_NAME + "/api/port", String.class);
    }

}
