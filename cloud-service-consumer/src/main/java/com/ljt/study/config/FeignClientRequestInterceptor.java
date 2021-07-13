package com.ljt.study.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Feign 请求拦截器
 *
 * @author LiJingTang
 * @date 2021-07-09 17:12
 */
@Slf4j
@Component
public class FeignClientRequestInterceptor implements RequestInterceptor {

    public static final String TOKEN = "token";

    @Override
    public void apply(RequestTemplate template) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

        log.info("InheritableThreadLocal：{}", ContextHolder.THREAD_LOCAL.get());
        log.info("HystrixRequestVariableDefault：{}", ContextHolder.HYSTRIX_VARIABLE.get());
        log.info("requestAttributes：{}", requestAttributes);

        if (requestAttributes instanceof ServletRequestAttributes) {
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            String token = request.getHeader(TOKEN);
            log.info("request-header: {}", token);

            if (StringUtils.isNotBlank(token)) {
                template.header(TOKEN, token);
            }
        }
    }

}
