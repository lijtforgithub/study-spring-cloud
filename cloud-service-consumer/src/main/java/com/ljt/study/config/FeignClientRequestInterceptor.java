package com.ljt.study.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import io.jmnarloch.spring.cloud.ribbon.support.RibbonFilterContextHolder;
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

        log.info("拦截器 {}", RibbonFilterContextHolder.getCurrentContext().get("grey-version"));

        if (requestAttributes instanceof ServletRequestAttributes) {
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            String token = request.getHeader(TOKEN);

            if (StringUtils.isNotBlank(token)) {
                template.header(TOKEN, token);
            }
        }
    }

}
