package com.ljt.study.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "alibaba-nacos-service", contextId = "alibaba-nacos-service-feign", path = "/feign")
public interface NacosServiceFeignClient {

    @PostMapping("/saveT")
    Integer saveT(@RequestParam(name = "key") String key);

}