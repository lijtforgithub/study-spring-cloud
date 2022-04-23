package com.ljt.study.config;

import com.ljt.study.factory.CustomCacheRequestBodyGatewayFilterFactory;
import com.ljt.study.filter.RequestLogFilter;
import org.springframework.cloud.gateway.config.conditional.ConditionalOnEnabledFilter;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.factory.rewrite.ModifyResponseBodyGatewayFilterFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;

import java.security.Principal;

/**
 * @author LiJingTang
 * @date 2022-04-17 13:36
 */
@Configuration
public class FilterConfig {

    @Bean
    @ConditionalOnEnabledFilter
    public CustomCacheRequestBodyGatewayFilterFactory customCacheRequestBodyGatewayFilterFactory() {
        return new CustomCacheRequestBodyGatewayFilterFactory();
    }

    @Bean
    RequestLogFilter requestLogFilter(ModifyResponseBodyGatewayFilterFactory factory) {
        return new RequestLogFilter(factory);
    }

    @Bean
    public GlobalFilter customGlobalFilter() {
        return (exchange, chain) -> exchange.getPrincipal()
                .map(Principal::getName)
                .defaultIfEmpty("Default User")
                .map(userName -> {
                    // adds header to proxied request
                    exchange.getRequest().mutate().header("CUSTOM-REQUEST-HEADER", userName).build();
                    return exchange;
                })
                .flatMap(chain::filter);
    }

    @Bean
    public GlobalFilter customGlobalPostFilter() {
        return (exchange, chain) -> chain.filter(exchange)
                .then(Mono.just(exchange))
                .map(serverWebExchange -> {
                    // adds header to response
                    serverWebExchange.getResponse().getHeaders().set("CUSTOM-RESPONSE-HEADER",
                            HttpStatus.OK.equals(serverWebExchange.getResponse().getStatusCode()) ? "It worked" : "It did not work");
                    return serverWebExchange;
                })
                .then();
    }

}
