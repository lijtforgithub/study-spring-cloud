package com.ljt.study.cloud.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import io.jmnarloch.spring.cloud.ribbon.support.RibbonFilterContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static org.apache.commons.lang.StringUtils.EMPTY;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.RIBBON_ROUTING_FILTER_ORDER;

/**
 * 灰度发布
 *
 * @author LiJingTang
 * @date 2021-05-29 14:48
 */
@Slf4j
@Component
public class GreyFilter extends ZuulFilter {

    @Autowired
    private ZuulProperties zuulProperties;

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
        // 可以用来设置是否走当前过滤器
        return false;
    }

    @Override
    public Object run() {
        log.info("灰度发布");
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        final String uri = request.getRequestURI().replaceFirst(zuulProperties.getPrefix(), EMPTY);
        final int index = uri.indexOf("/", 1);
        final String path = uri.substring(0, index + 1);
        Optional<ZuulProperties.ZuulRoute> optional = zuulProperties.getRoutes().values().stream().filter(r -> r.getPath().startsWith(path)).findFirst();
        optional.ifPresent(route -> log.info("服务：{}", route.getServiceId()));

        String userId = request.getHeader("user-id");
        // 规则可以配置的数据库或缓存 meta-ver配置在eureka.instance.metadata-map 里也可以调用http接口修改
        if (StringUtils.isNotBlank(userId)) {
            RibbonFilterContextHolder.getCurrentContext().add("grey-version", Integer.parseInt(userId) <= 100 ? "v1" : "v2");
        }

        return null;
    }

}
