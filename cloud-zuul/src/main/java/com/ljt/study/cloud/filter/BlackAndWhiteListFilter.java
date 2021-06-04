package com.ljt.study.cloud.filter;

import com.ljt.study.cloud.properties.UrlMappingProperties;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.ljt.study.cloud.filter.FilterUtils.AUTH_KEY;

/**
 * 黑白名单
 *
 * @author LiJingTang
 * @date 2021-06-02 17:41
 */
@Slf4j
@Component
public class BlackAndWhiteListFilter extends ZuulFilter {

    @Autowired
    private UrlMappingProperties properties;

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return Integer.MIN_VALUE;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @SneakyThrows
    @Override
    public Object run() {
        log.info("黑白名单");
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        HttpServletResponse response = context.getResponse();
        final String uri = request.getRequestURI();

        if (properties.getBlackList().contains(uri)) {
            // 黑名单没有想到实际用途 路径和yml配置没有匹配 直接404
            context.setSendZuulResponse(false);
            context.setResponseStatusCode(HttpStatus.SC_NOT_ACCEPTABLE);
            FilterUtils.write(response, "黑名单");
        } else if (properties.getWhiteList().contains(uri)) {
            // 白名单 不做认证和授权
            context.set(AUTH_KEY, Boolean.FALSE);
        }

        return null;
    }

}
