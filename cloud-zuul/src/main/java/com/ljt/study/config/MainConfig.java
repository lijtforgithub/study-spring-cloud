package com.ljt.study.config;

import com.ljt.study.fallback.CustomFallback;
import com.ljt.study.filter.*;
import com.ljt.study.timeout.EnableUrlRibbonTimeout;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

/**
 * @author LiJingTang
 * @date 2021-12-21 10:54
 */
@Slf4j
//@Import({RedisConfig.class, ShiroConfig.class})
@Configuration
@EnableUrlRibbonTimeout
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

}
