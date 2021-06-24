package com.ljt.study.sse;

import com.ljt.study.api.ServiceApi;
import com.ljt.study.hystrix.RestTemplateHystrix;
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
@RestController
@RequestMapping("/port")
public class RestTemplateController {

    @Autowired
    private LoadBalancerClient balancerClient;
    @Autowired
    private RestTemplateHystrix restTemplateHystrix;

    @GetMapping()
    public String getPort() {
        // 负载均衡
        ServiceInstance instance = balancerClient.choose(ServiceApi.APP_NAME);
        String url = instance.getUri().toString() + "/api/port";
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
