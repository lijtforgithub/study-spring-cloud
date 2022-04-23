package com.ljt.study.factory;

import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.NettyWriteResponseFilter;
import org.springframework.cloud.gateway.filter.factory.CacheRequestBodyGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.Set;

/**
 * @author LiJingTang
 * @date 2022-04-23 9:50
 */
@Slf4j
public class CustomCacheRequestBodyGatewayFilterFactory extends CacheRequestBodyGatewayFilterFactory {

    private static final Set<MediaType> TYPES = Sets.newHashSet(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE));

    public static boolean isCache(MediaType mediaType) {
        if (Objects.isNull(mediaType)) {
            return false;
        }

        return TYPES.stream().anyMatch(mediaType::includes);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return new CacheRequestBodyGatewayFilter(config);
    }

    private static class CacheRequestBodyGatewayFilter implements GatewayFilter, Ordered {

        private final Config config;

        public CacheRequestBodyGatewayFilter(Config config) {
            this.config = config;
        }

        @Override
        public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
            MediaType contentType = exchange.getRequest().getHeaders().getContentType();
            log.info("{} | {}", exchange.getRequest().getPath(), contentType);
            if (isCache(contentType)) {
                return new CacheRequestBodyGatewayFilterFactory().apply(config).filter(exchange, chain);
            }
            return chain.filter(exchange);
        }

        @Override
        public int getOrder() {
            return NettyWriteResponseFilter.WRITE_RESPONSE_FILTER_ORDER - 2;
        }
    }

}
