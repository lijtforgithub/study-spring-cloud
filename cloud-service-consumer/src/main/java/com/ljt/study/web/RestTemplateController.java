package com.ljt.study.web;

import com.ljt.study.api.ServiceApi;
import com.ljt.study.hystrix.RestTemplateHystrix;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.ObjectInputStream;
import java.net.HttpURLConnection;
import java.net.URL;

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
    private RestTemplateHystrix restTemplateHystrix;

    @GetMapping("/port")
    public String getPort() {
        // 负载均衡
        ServiceInstance instance = balancerClient.choose(ServiceApi.APP_NAME);
        String url = instance.getUri().toString() + "/api/port";
        // 没有被代理的RestTemplate只能根据IP地址
        return new RestTemplate().getForObject(url, String.class);
    }

    @GetMapping("/lb")
    public String getPortWithLb() {
        return restTemplateHystrix.getPort();
    }

    @GetMapping("/hystrix")
    public String getPortWithHystrix() {
        return restTemplateHystrix.getPortWithHystrix();
    }

}
