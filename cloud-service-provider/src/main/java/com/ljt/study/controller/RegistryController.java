package com.ljt.study.controller;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author LiJingTang
 * @date 2020-06-23 15:13
 */
@Slf4j
@RestController
@RequestMapping("/registry")
public class RegistryController {

    @Value("${spring.application.name}")
    private String appName;

    @Autowired
    private DiscoveryClient client;

    @SneakyThrows
    @GetMapping("/apps")
    public List<String> findApps() {
        TimeUnit.SECONDS.sleep(10);
        return client.getServices();
    }

    @GetMapping("/instance")
    public ServiceInstance instance() {
        List<ServiceInstance> instances = client.getInstances(appName);
        if (CollectionUtils.isEmpty(instances)) {
            return null;
        }

        return instances.get(0);
    }

}
