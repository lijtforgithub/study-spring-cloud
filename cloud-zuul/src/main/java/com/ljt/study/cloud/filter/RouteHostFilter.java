package com.ljt.study.cloud.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.net.URL;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.RIBBON_ROUTING_FILTER_ORDER;

/**
 * 重定向到指定主机
 *
 * @author LiJingTang
 * @date 2021-06-02 15:37
 */
@Slf4j
@Component
public class RouteHostFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return FilterConstants.ROUTE_TYPE;
    }

    @Override
    public int filterOrder() {
        // 必须在 RIBBON_ROUTING_FILTER_ORDER 之前
        return RIBBON_ROUTING_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {
        return false;
    }

    @SneakyThrows
    @Override
    public Object run() {
        log.info("重定向到指定主机");
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        final String uri = request.getRequestURI();

        if (uri.contains("/route-host/")) {
            // 路由到具体地址
            context.set(FilterConstants.SERVICE_ID_KEY);
            context.setRouteHost(new URL("http://localhost:8001/registry/apps"));
        }

        return null;
    }

}
