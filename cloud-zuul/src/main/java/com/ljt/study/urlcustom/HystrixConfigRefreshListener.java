package com.ljt.study.urlcustom;

import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.strategy.properties.HystrixPropertiesFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.event.EventListener;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static com.ljt.study.urlcustom.CustomHttpClientRibbonCommand.KEY;
import static com.ljt.study.urlcustom.UrlCustomProperties.PREFIX;

/**
 * @author LiJingTang
 * @date 2021-12-22 10:52
 */
@Slf4j
public class HystrixConfigRefreshListener {

    private static final String FIELD_COMMAND_PROPERTIES = "commandProperties";

    @EventListener
    public void refresh(EnvironmentChangeEvent event) {
        List<String> keys = event.getKeys().stream().filter(key -> key.startsWith(PREFIX)).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(keys)) {
            return;
        }

        log.info("配置项刷新: {}", keys);

        try {
            Field field = HystrixPropertiesFactory.class.getDeclaredField(FIELD_COMMAND_PROPERTIES);
            ReflectionUtils.makeAccessible(field);
            Object obj = field.get(null);

            ConcurrentHashMap<String, HystrixCommandProperties> map = (ConcurrentHashMap<String, HystrixCommandProperties>) obj;
            if (map.containsKey(KEY)) {
                map.remove(KEY);
                log.info("hystrix配置缓存删除 {}", KEY);
            }

            log.info("更新hystrix配置缓存成功");
        } catch (NoSuchFieldException | IllegalAccessException e) {
            log.error("更新hystrix配置缓存失败", e);
        }
    }

}
