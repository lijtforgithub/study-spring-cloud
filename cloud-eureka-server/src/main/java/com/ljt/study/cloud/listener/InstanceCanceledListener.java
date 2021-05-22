package com.ljt.study.cloud.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.eureka.server.event.EurekaInstanceCanceledEvent;
import org.springframework.cloud.netflix.eureka.server.event.EurekaInstanceRegisteredEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author LiJingTang
 * @date 2021-05-22 15:37
 */
@Slf4j
@Component
public class InstanceCanceledListener {

    @EventListener
    public void listener(EurekaInstanceRegisteredEvent event) {
        log.info("服务注册：{} {}", event.getInstanceInfo().getAppName(), event.getInstanceInfo().getInstanceId());
    }

    @EventListener
    public void listener(EurekaInstanceCanceledEvent event) {
        log.info("服务下线：{} {}", event.getAppName(), event.getServerId());
    }

}
