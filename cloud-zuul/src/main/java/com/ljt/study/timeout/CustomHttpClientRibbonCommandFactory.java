package com.ljt.study.timeout;

import com.netflix.client.config.IClientConfig;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.cloud.netflix.ribbon.apache.RibbonLoadBalancingHttpClient;
import org.springframework.cloud.netflix.ribbon.support.RibbonCommandContext;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.cloud.netflix.zuul.filters.route.RibbonCommand;
import org.springframework.cloud.netflix.zuul.filters.route.support.AbstractRibbonCommandFactory;
import org.springframework.util.PathMatcher;

import java.util.Collections;
import java.util.Set;

/**
 * 自定义创建RibbonCommand的工厂
 *
 * @author LiJingTang
 * @date 2021-12-21 11:14
 */
public class CustomHttpClientRibbonCommandFactory extends AbstractRibbonCommandFactory {

    private final SpringClientFactory clientFactory;
    private final ZuulProperties zuulProperties;
    private final RibbonTimeoutProperties ribbonTimeoutProperties;
    private final PathMatcher pathMatcher;

    public CustomHttpClientRibbonCommandFactory(SpringClientFactory clientFactory, ZuulProperties zuulProperties, Set<FallbackProvider> fallbackProviders,
                                                RibbonTimeoutProperties ribbonTimeoutProperties, PathMatcher pathMatcher) {
        super(fallbackProviders != null ? fallbackProviders : Collections.emptySet());
        this.clientFactory = clientFactory;
        this.zuulProperties = zuulProperties;
        this.ribbonTimeoutProperties = ribbonTimeoutProperties;
        this.pathMatcher = pathMatcher;
    }

    @Override
    public RibbonCommand create(RibbonCommandContext context) {
        FallbackProvider fallbackProvider = getFallbackProvider(context.getServiceId());
        final String serviceId = context.getServiceId();
        final RibbonLoadBalancingHttpClient client = clientFactory.getClient(serviceId, RibbonLoadBalancingHttpClient.class);
        client.setLoadBalancer(clientFactory.getLoadBalancer(serviceId));
        final IClientConfig config = clientFactory.getClientConfig(serviceId);

        return new CustomHttpClientRibbonCommand(serviceId, client, context, zuulProperties,
                fallbackProvider, config, ribbonTimeoutProperties, pathMatcher);
    }

}
