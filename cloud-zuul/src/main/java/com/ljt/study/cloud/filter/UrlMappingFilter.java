package com.ljt.study.cloud.filter;

import com.ljt.study.cloud.properties.UrlMappingProperties;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.RIBBON_ROUTING_FILTER_ORDER;

/**
 * 重定向同一服务或不同服务
 *
 * @author LiJingTang
 * @date 2021-06-01 14:05
 */
@Slf4j
@Component
public class UrlMappingFilter extends ZuulFilter {

    @Autowired
    private UrlMappingProperties properties;

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
        return RequestContext.getCurrentContext().sendZuulResponse();
    }

    @SneakyThrows
    @Override
    public Object run() {
        log.info("重定向同一服务或不同服务");
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        final String uri = request.getRequestURI();
        log.info("{} : {} {}", uri, context.get(FilterConstants.SERVICE_ID_KEY), context.get(FilterConstants.REQUEST_URI_KEY));

        Map<String, UrlMappingProperties.Item> map = properties.getRedirectList().stream()
                .collect(Collectors.toMap(UrlMappingProperties.Item::getOrigin, Function.identity()));
        UrlMappingProperties.Item item = map.get(uri);

        if (Objects.nonNull(item)) {
            // 路由到同一服务的不同地址
            context.set(FilterConstants.REQUEST_URI_KEY, item.getRedirect());
            if (StringUtils.isNotBlank(item.getServiceId())) {
                // 路由到不同服务的地址
                context.set(FilterConstants.SERVICE_ID_KEY, item.getServiceId());
            }
        }

        return null;
    }

}
