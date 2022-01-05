package com.ljt.study.rest;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.autoconfigure.LettuceFactory;
import com.alicp.jetcache.autoconfigure.RedisLettuceAutoConfiguration;
import io.lettuce.core.RedisClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisKeyExpiredEvent;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.retry.annotation.EnableRetry;

import static com.ljt.study.rest.RestConstants.*;

/**
 * @author LiJingTang
 * @date 2022-01-04 14:04
 */
@Slf4j
@EnableCreateCacheAnnotation
@EnableRetry(proxyTargetClass = true)
@Configuration
public class RestConfig {

    @Bean
    public RestTemplateWrapper restTemplateWrapper() {
        return new RestTemplateWrapper();
    }

    @Bean(name = "defaultClient")
    @DependsOn(RedisLettuceAutoConfiguration.AUTO_INIT_BEAN_NAME)
    public LettuceFactory defaultClient() {
        return new LettuceFactory("remote.default", RedisClient.class);
    }

    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory redisConnectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);

        return container;
    }

    @Bean
    KeyExpirationEventMessageListener listener(RedisMessageListenerContainer container) {
        return new KeyExpirationEventMessageListener(container);
    }

    @EventListener
    public void keyExpire(RedisKeyExpiredEvent<String> event) {
        final String key = new String(event.getSource());
        log.info("key过期：{}", key);
        if (key.startsWith(CACHE_PREFIX + CACHE_LISTENER)) {
            restTemplateWrapper().refreshToken();
        }
    }

}
