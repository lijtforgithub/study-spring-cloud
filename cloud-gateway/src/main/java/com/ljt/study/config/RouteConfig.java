package com.ljt.study.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import static org.springframework.cloud.gateway.support.RouteMetadataUtils.CONNECT_TIMEOUT_ATTR;
import static org.springframework.cloud.gateway.support.RouteMetadataUtils.RESPONSE_TIMEOUT_ATTR;

/**
 * @author LiJingTang
 * @date 2022-04-17 10:56
 */
//@Configuration
public class RouteConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/bd")
                        .filters(f -> f.addResponseHeader("X-TestHeader", "百度"))
                        .metadata(CONNECT_TIMEOUT_ATTR, 200)
                        .metadata(RESPONSE_TIMEOUT_ATTR, 200)
                        .uri("forward:///fw/baidu")
                )
                .build();
    }

}
