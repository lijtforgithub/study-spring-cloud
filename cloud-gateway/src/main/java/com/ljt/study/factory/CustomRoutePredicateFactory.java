package com.ljt.study.factory;

import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;

import java.util.function.Predicate;

/**
 * @author LiJingTang
 * @date 2022-04-17 13:39
 */
public class CustomRoutePredicateFactory extends AbstractRoutePredicateFactory<CustomRoutePredicateFactory.Config> {

    public CustomRoutePredicateFactory() {
        super(Config.class);
    }

    @Override
    public Predicate<ServerWebExchange> apply(Config config) {
        // grab configuration from Config object
        return exchange -> {
            // grab the request
            ServerHttpRequest request = exchange.getRequest();
            // take information from the request to see if it
            // matches configuration.
            return matches(config, request);
        };
    }

    private boolean matches(Config config, ServerHttpRequest request) {
        return true;
    }

    public static class Config {
        //Put the configuration properties for your filter here
    }

}
