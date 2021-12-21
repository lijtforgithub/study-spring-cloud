package com.ljt.study.filter;

import com.ljt.study.properties.UrlMappingProperties;
import com.ljt.study.shiro.ShiroUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.ljt.study.filter.FilterUtils.AUTH_KEY;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.DEBUG_FILTER_ORDER;

/**
 * 认证和授权
 *
 * @author LiJingTang
 * @version 2017年12月12日 下午7:13:12
 */
@Slf4j
public class AuthFilter extends ZuulFilter {

    private static final String TOKEN = "token";

    @Autowired
    private UrlMappingProperties properties;

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return DEBUG_FILTER_ORDER + 2;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext context = RequestContext.getCurrentContext();
        return context.sendZuulResponse() && !Boolean.FALSE.equals(context.get(AUTH_KEY));
    }

    @SneakyThrows
    @Override
    public Object run() {
        log.info("认证和鉴权");
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        HttpServletResponse response = context.getResponse();
        final String uri = request.getRequestURI();
        final String token = request.getHeader(TOKEN);

        // 为了简单测试shiro 没生成token 不校验token
        if (ShiroUtils.isLogin()) {
            return null;
        }

        if (StringUtils.isBlank(token)) {
            // 走后面的过滤器 不做服务转发了
            context.setSendZuulResponse(false);
            context.setResponseStatusCode(HttpStatus.SC_UNAUTHORIZED);
            FilterUtils.write(response, "请先登录");
        } else {
            log.info("校验token");
            if (properties.getFreeList().contains(uri)) {
                log.info("自由端点认证不鉴权：{}", uri);
            } else {
                log.info("开始鉴权: {}", uri);
            }
        }

        return null;
    }

}