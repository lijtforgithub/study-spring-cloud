package com.ljt.study.urlcustom;

import com.netflix.client.config.IClientConfig;
import com.netflix.config.DynamicIntProperty;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolKey;
import com.netflix.hystrix.strategy.properties.HystrixPropertiesFactory;
import com.netflix.zuul.constants.ZuulConstants;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.ribbon.apache.RibbonApacheHttpRequest;
import org.springframework.cloud.netflix.ribbon.apache.RibbonApacheHttpResponse;
import org.springframework.cloud.netflix.ribbon.apache.RibbonLoadBalancingHttpClient;
import org.springframework.cloud.netflix.ribbon.support.RibbonCommandContext;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.cloud.netflix.zuul.filters.route.support.AbstractRibbonCommand;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.util.PathMatcher;

import java.util.Objects;
import java.util.Set;

/**
 * 自定义RibbonCommand 设置url级别的hystrix超时时间
 *
 * @author LiJingTang
 * @date 2021-12-21 11:13
 */
@Slf4j
@Setter
@Getter
class CustomHttpClientRibbonCommand extends
        AbstractRibbonCommand<RibbonLoadBalancingHttpClient, RibbonApacheHttpRequest, RibbonApacheHttpResponse> {

    static final String KEY = "hystrix.custom-timeout.key";
    private static final PathMatcher PATH_MATCHER = new AntPathMatcher();

    private final UrlCustomProperties urlCustomProperties;

    public CustomHttpClientRibbonCommand(String commandKey, RibbonLoadBalancingHttpClient client, RibbonCommandContext context,
                                         ZuulProperties zuulProperties, FallbackProvider zuulFallbackProvider, IClientConfig config,
                                         UrlCustomProperties urlCustomProperties) {
        super(getSetter(commandKey, zuulProperties, config, context, urlCustomProperties),
                client, context, zuulFallbackProvider, config);
        this.urlCustomProperties = urlCustomProperties;
    }

    private static Setter getSetter(final String commandKey, ZuulProperties zuulProperties, IClientConfig config,
                                      RibbonCommandContext context, UrlCustomProperties urlCustomProperties) {
        final String key = getCommandKey(commandKey, context, urlCustomProperties);
        Setter commandSetter = Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("RibbonCommand")).andCommandKey(HystrixCommandKey.Factory.asKey(key));
        final HystrixCommandProperties.Setter setter = createSetter(config, commandKey, zuulProperties, context, urlCustomProperties, KEY.equals(key));

        if (zuulProperties.getRibbonIsolationStrategy() == HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE) {
            final String name = ZuulConstants.ZUUL_EUREKA + commandKey + ".semaphore.maxSemaphores";
            final DynamicIntProperty value = DynamicPropertyFactory.getInstance().getIntProperty(name, zuulProperties.getSemaphore().getMaxSemaphores());
            setter.withExecutionIsolationSemaphoreMaxConcurrentRequests(value.get());
        } else if (zuulProperties.getThreadPool().isUseSeparateThreadPools()) {
            final String threadPoolKey = zuulProperties.getThreadPool().getThreadPoolKeyPrefix() + commandKey;
            commandSetter.andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey(threadPoolKey));
        }

        return commandSetter.andCommandPropertiesDefaults(setter);
    }

    /**
     * @see HystrixPropertiesFactory#getCommandProperties(com.netflix.hystrix.HystrixCommandKey, com.netflix.hystrix.HystrixCommandProperties.Setter)
     *
     * 如果这里不重写key针对以下配置 createSetter设置了hystrix超时时间也会被应用缓存覆盖
     * hystrix:
     *   command:
     *     service-provider:
     *       execution:
     *         isolation:
     *           thread:
     *             timeoutInMilliseconds: 3000
     */
    private static String getCommandKey(String commandKey, RibbonCommandContext context, UrlCustomProperties urlCustomProperties) {
        return isMatch(context, urlCustomProperties) ? KEY : commandKey;
    }

    private static HystrixCommandProperties.Setter createSetter(IClientConfig config, String commandKey, ZuulProperties zuulProperties,
                                                                RibbonCommandContext context, UrlCustomProperties urlCustomProperties, boolean isMatch) {
        int hystrixTimeout = getHystrixTimeout(config, commandKey);
        /*
         * 重新设置hystrix的超时时间 如果不设置且小于ribbon超时时间 那么配置的接口的超时时间一样没意义
         * 这里暂时设置为 SocketTimeout + ConnectTimeout
         */
        if (isMatch && hystrixTimeout < urlCustomProperties.getSocketTimeout() + urlCustomProperties.getConnectTimeout()) {
            final String serviceId = context.getServiceId();
            final String url = context.getUri();
            hystrixTimeout = urlCustomProperties.getSocketTimeout() + urlCustomProperties.getConnectTimeout();
            log.info("hystrix: 更新[{}]{}超时时间 {}", serviceId, url, hystrixTimeout);
        }

        return HystrixCommandProperties.Setter()
                .withExecutionIsolationStrategy(zuulProperties.getRibbonIsolationStrategy())
                .withExecutionTimeoutInMilliseconds(hystrixTimeout);
    }


    /**
     * 请求地址是否配置了单独的超时时间
     */
    static boolean isMatch(RibbonCommandContext context, UrlCustomProperties urlCustomProperties) {
        if (Objects.nonNull(urlCustomProperties) && !CollectionUtils.isEmpty(urlCustomProperties.getUrlMap())) {
            Set<String> set = urlCustomProperties.getUrlMap().get(context.getServiceId());
            final String url = context.getUri();
            return !CollectionUtils.isEmpty(set) && (set.contains(url) || set.stream().anyMatch(s -> PATH_MATCHER.match(s, url)));
        }

        return false;
    }


    @Override
    protected RibbonApacheHttpRequest createRequest() {
        return new CustomRibbonApacheHttpRequest(context, urlCustomProperties);
    }

}
