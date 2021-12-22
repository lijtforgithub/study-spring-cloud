package com.ljt.study.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.cloud.context.scope.refresh.RefreshScopeRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * https://my.oschina.net/yugj/blog/4458038
 *
 * @author LiJingTang
 * @date 2021-12-22 11:26
 */
@Slf4j
@Component
public class ConfigRefreshEventListener {

    @EventListener
    public void configRefresh(RefreshScopeRefreshedEvent event) {
        log.info("RefreshScopeRefreshedEvent 配置项刷新: {}", event);
    }

    @EventListener
    public void configRefresh(EnvironmentChangeEvent event) {
        log.info("EnvironmentChangeEvent 配置项刷新: {}", event);
    }

}
