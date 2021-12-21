package com.ljt.study.config;

import com.ljt.study.fallback.CustomFallback;
import com.ljt.study.filter.*;
import com.ljt.study.properties.RibbonTimeoutProperties;
import com.ljt.study.ribbon.CustomHttpClientRibbonCommandFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.cloud.netflix.zuul.filters.route.RibbonCommandFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.PathMatcher;

import java.util.Set;

import static com.ljt.study.properties.RibbonTimeoutProperties.ENABLE;

/**
 * @author LiJingTang
 * @date 2021-12-21 10:54
 */
@Slf4j
//@Import({RedisConfig.class, ShiroConfig.class})
@Configuration
public class MainConfig {

    public CustomFallback customFallback() {
        return new CustomFallback();
    }

    public AuthFilter authFilter() {
        return new AuthFilter();
    }

    public BlackAndWhiteListFilter blackAndWhiteListFilter() {
        return new BlackAndWhiteListFilter();
    }

    public GreyFilter greyFilter() {
        return new GreyFilter();
    }

    public RouteHostFilter routeHostFilter() {
        return new RouteHostFilter();
    }

    public UrlMappingFilter urlMappingFilter() {
        return new UrlMappingFilter();
    }

    @Autowired
    private RibbonTimeoutProperties ribbonTimeoutProperties;
    @Autowired
    private PathMatcher pathMatcher;

    @Bean
    @ConditionalOnProperty(name = ENABLE, havingValue = "true")
    public RibbonCommandFactory<?> ribbonCommandFactory(SpringClientFactory clientFactory, ZuulProperties zuulProperties,
                                                        @Autowired(required = false) Set<FallbackProvider> fallbackProviders) {
        log.info("自定义 CustomHttpClientRibbonCommandFactory");
        return new CustomHttpClientRibbonCommandFactory(clientFactory, zuulProperties, fallbackProviders, ribbonTimeoutProperties, pathMatcher);
    }

}
