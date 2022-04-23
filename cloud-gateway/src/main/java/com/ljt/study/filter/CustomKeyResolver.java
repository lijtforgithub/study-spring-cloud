package com.ljt.study.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;
import java.util.Objects;

/**
 * @author LiJingTang
 * @date 2022-04-23 15:20
 */
@Slf4j
@Component
public class CustomKeyResolver implements KeyResolver {

    @Override
    public Mono<String> resolve(ServerWebExchange exchange) {
        InetSocketAddress address = exchange.getRequest().getRemoteAddress();
        String hostName = Objects.nonNull(address) ? address.getAddress().getHostAddress() : "";
        log.info("KeyResolver = {}", hostName);
        return Mono.just(hostName);
    }

}
