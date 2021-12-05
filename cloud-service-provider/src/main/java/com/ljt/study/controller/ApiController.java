package com.ljt.study.controller;

import com.ljt.study.api.ServiceApi;
import com.ljt.study.api.dto.UserDTO;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

/**
 * @author LiJingTang
 * @date 2020-07-01 10:38
 */
@Slf4j
@RestController
public class ApiController implements ServiceApi {

    @Value("${server.port}")
    private String port;
    @Value("${spring.application.name}")
    private String appName;

    @Override
    public String getCurrentTime() {
//        int i = 1 / 0;
        return LocalDateTime.now().toString();
    }

    @Override
    public String getServicePort() {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            log.info("token = {}", request.getHeader("token"));
        }
        return String.format("%s From Port: %s", appName, port);
    }

    @Override
    public UserDTO getUser() {
        return UserDTO.builder().id(100).name("日志").gender(0).account("0123456").address(port).build();
    }

    @SneakyThrows
    @Override
    public byte[] byteArray() {
        return IOUtils.toByteArray(getResource().getInputStream());
    }

    private ClassPathResource getResource() {
        return new ClassPathResource("bootstrap.yml");
    }

    @SneakyThrows
    @Override
    public void resp() {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            HttpServletResponse response = ((ServletRequestAttributes) requestAttributes).getResponse();
            response.setContentType("text/plain; charset=utf-8");
            response.getOutputStream().write(byteArray());
        }
    }

    @SneakyThrows
    @Override
    public InputStream output() {
        return getResource().getInputStream();
    }

    @SneakyThrows
    @Override
    public void input(InputStream input) {
        log.info(IOUtils.readLines(input, StandardCharsets.UTF_8).toString());
    }

}
