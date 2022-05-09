package com.ljt.study.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * @author LiJingTang
 * @date 2021-12-05 12:04
 */
@Slf4j
@RestController
public class NacosController {

    @Value("${server.port}")
    private String port;
    @Value("${spring.application.name}")
    private String appName;

    @RequestMapping("/port")
    public String getServicePort(HttpServletRequest request, @RequestHeader("X-Request") String addHeader) {
        log.info("X-Request={}", addHeader);
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            log.info("{} = {}", name, request.getHeader(name));
        }
        return String.format("%s 来自 Port: %s", appName, port);
    }

    @PostMapping("/upload")
    public String upload(MultipartFile file) {
        log.info("文件{} 大小{}", file.getOriginalFilename(), file.getSize());
        return file.getOriginalFilename();
    }

}