package com.ljt.study.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author LiJingTang
 * @date 2021-12-05 12:04
 */
@Slf4j
@RestController
public class NacosController {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ServerProperties serverProperties;

    @GetMapping("/port")
    public String getServicePort() {
        return restTemplate.getForObject("http://alibaba-nacos-service/port", String.class);
    }

    @GetMapping("/self/port")
    public Integer getSelfPort() {
        return serverProperties.getPort();
    }

    @PostMapping("/upload")
    public String upload(MultipartFile file) {
        log.info("文件{} 大小{}", file.getOriginalFilename(), file.getSize());
        return file.getOriginalFilename();
    }

}
