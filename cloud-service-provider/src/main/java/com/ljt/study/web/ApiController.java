package com.ljt.study.sse;

import com.ljt.study.api.ServiceApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
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

}
