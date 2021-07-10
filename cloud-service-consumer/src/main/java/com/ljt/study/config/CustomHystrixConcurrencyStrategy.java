package com.ljt.study.config;

import com.netflix.hystrix.strategy.HystrixPlugins;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy;
import com.netflix.hystrix.strategy.eventnotifier.HystrixEventNotifier;
import com.netflix.hystrix.strategy.executionhook.HystrixCommandExecutionHook;
import com.netflix.hystrix.strategy.metrics.HystrixMetricsPublisher;
import com.netflix.hystrix.strategy.properties.HystrixPropertiesStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.annotation.PostConstruct;
import java.util.concurrent.Callable;

/**
 * 解决 Hystrix 线程池传参策略
 *
 * @author LiJingTang
 * @date 2021-07-09 17:08
 *
 * @see org.springframework.cloud.netflix.hystrix.security.HystrixSecurityAutoConfiguration
 * @see org.springframework.cloud.netflix.hystrix.security.SecurityContextConcurrencyStrategy
 * @see org.springframework.cloud.sleuth.instrument.hystrix.SleuthHystrixConcurrencyStrategy
 */
@Slf4j
public class CustomHystrixConcurrencyStrategy extends HystrixConcurrencyStrategy {

    @PostConstruct
    public void init() {
        HystrixEventNotifier eventNotifier = HystrixPlugins.getInstance().getEventNotifier();
        HystrixMetricsPublisher metricsPublisher = HystrixPlugins.getInstance().getMetricsPublisher();
        HystrixPropertiesStrategy propertiesStrategy = HystrixPlugins.getInstance().getPropertiesStrategy();
        HystrixCommandExecutionHook commandExecutionHook = HystrixPlugins.getInstance().getCommandExecutionHook();

        HystrixPlugins.reset();

        HystrixPlugins.getInstance().registerConcurrencyStrategy(this);
        HystrixPlugins.getInstance().registerEventNotifier(eventNotifier);
        HystrixPlugins.getInstance().registerMetricsPublisher(metricsPublisher);
        HystrixPlugins.getInstance().registerPropertiesStrategy(propertiesStrategy);
        HystrixPlugins.getInstance().registerCommandExecutionHook(commandExecutionHook);
    }

    @Override
    public <T> Callable<T> wrapCallable(Callable<T> callable) {
        return new WrappedCallable<>(callable, RequestContextHolder.getRequestAttributes());
    }

    /**
     * @see org.springframework.security.concurrent.DelegatingSecurityContextCallable
     */
    private static class WrappedCallable<T> implements Callable<T> {

        private final Callable<T> target;
        private final RequestAttributes requestAttributes;

        public WrappedCallable(Callable<T> target, RequestAttributes requestAttributes) {
            this.target = target;
            this.requestAttributes = requestAttributes;
        }

        @Override
        public T call() throws Exception {
            try {
                RequestContextHolder.setRequestAttributes(requestAttributes);
                return target.call();
            } finally {
                RequestContextHolder.resetRequestAttributes();
            }
        }
    }

}
