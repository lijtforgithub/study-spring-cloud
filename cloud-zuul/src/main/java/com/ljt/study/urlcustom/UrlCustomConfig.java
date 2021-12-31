package com.ljt.study.urlcustom;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.cloud.netflix.zuul.filters.route.RibbonCommandFactory;
import org.springframework.context.annotation.Bean;

import java.util.Set;

/**
 * @author LiJingTang
 * @date 2021-12-22 15:27
 */
@Slf4j
public class UrlCustomConfig {

    @Autowired
    private UrlCustomProperties urlCustomProperties;

    @Bean
    RibbonCommandFactory<?> ribbonCommandFactory(SpringClientFactory clientFactory, ZuulProperties zuulProperties,
                                                        @Autowired(required = false) Set<FallbackProvider> fallbackProviders) {
        log.info("自定义 CustomHttpClientRibbonCommandFactory");
        return new CustomHttpClientRibbonCommandFactory(clientFactory, zuulProperties, fallbackProviders, urlCustomProperties);
    }

    @Bean
    HystrixConfigRefreshListener hystrixConfigRefreshListener() {
        return new HystrixConfigRefreshListener();
    }

    @Bean
    UrlCustomProperties urlCustomProperties() {
        return new UrlCustomProperties();
    }

    @Bean
    PreReplaceUrlFilter preReplaceUrlFilter() {
        return new PreReplaceUrlFilter();
    }

}
