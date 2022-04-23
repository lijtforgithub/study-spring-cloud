package com.ljt.study.filter;

import com.ljt.study.factory.CustomCacheRequestBodyGatewayFilterFactory;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.NettyWriteResponseFilter;
import org.springframework.cloud.gateway.filter.factory.rewrite.ModifyResponseBodyGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.rewrite.RewriteFunction;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author LiJingTang
 * @date 2022-04-22 9:19
 */
@Slf4j
public class RequestLogFilter implements GlobalFilter, Ordered {

    private final GatewayFilter delegate;

    public RequestLogFilter(ModifyResponseBodyGatewayFilterFactory factory) {
        this.delegate = factory.apply(new ModifyResponseBodyGatewayFilterFactory.Config()
                .setInClass(byte[].class).setOutClass(byte[].class)
                .setRewriteFunction(new BodyRewriteFunction()));
    }

    @Override
    public int getOrder() {
        return NettyWriteResponseFilter.WRITE_RESPONSE_FILTER_ORDER - 1;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("请求日志filter开始");

        ServerHttpRequest request = exchange.getRequest();
        log.info("{}  | {} | {}", request.getURI(), request.getHeaders().getContentType(), request.getId());

        if (CustomCacheRequestBodyGatewayFilterFactory.isCache(request.getHeaders().getContentType())) {
            Object cachedBody = exchange.getAttribute(ServerWebExchangeUtils.CACHED_REQUEST_BODY_ATTR);
            log.info("请求body\n{}", cachedBody);
        }

        return delegate.filter(exchange, chain);
    }

    private static class BodyRewriteFunction implements RewriteFunction<byte[], byte[]> {

        @Override
        public Publisher<byte[]> apply(ServerWebExchange exchange, byte[] bytes) {
            ServerHttpResponse response = exchange.getResponse();
            log.info("{} 响应码{}", exchange.getRequest().getId(), response.getRawStatusCode());

            if (ServerWebExchangeUtils.isAlreadyRouted(exchange)) {
                log.info("响应body\n{}", new String(bytes));
            }

            return Mono.just(bytes);
        }
    }

}
