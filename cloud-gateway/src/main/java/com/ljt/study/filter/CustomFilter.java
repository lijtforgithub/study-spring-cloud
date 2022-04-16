package com.ljt.study.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Map;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_SCHEME_PREFIX_ATTR;
import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.URI_TEMPLATE_VARIABLES_ATTRIBUTE;

/**
 * @author LiJingTang
 * @date 2021-05-10 16:46
 */
@Slf4j
@Component
public class CustomFilter implements Ordered, GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        Object value = exchange.getAttributes().get(URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Object prefix = exchange.getAttributes().get(GATEWAY_SCHEME_PREFIX_ATTR);
        Map<String, String> uriVariables = ServerWebExchangeUtils.getUriTemplateVariables(exchange);
        String segment = uriVariables.get("segment");
        log.info("-----------value={} prefix={} segment={}", value, prefix, segment);

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return Integer.MAX_VALUE;
    }

}
