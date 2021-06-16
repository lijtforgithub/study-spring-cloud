package com.ljt.study.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.eureka.server.event.*;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author LiJingTang
 * @date 2021-05-22 15:37
 */
@Slf4j
@Component
public class InstanceListener {

    @EventListener
    public void listener(EurekaServerStartedEvent event) {
        log.info("注册中心 EurekaServerStarted");
    }
    @EventListener
    public void listener(EurekaRegistryAvailableEvent event) {
        log.info("注册中心 EurekaRegistryAvailable");
    }

    @EventListener
    public void listener(EurekaInstanceRegisteredEvent event) {
        log.info("服务注册{}：{} {}", event.isReplication(), event.getInstanceInfo().getAppName(), event.getInstanceInfo().getInstanceId());
    }

    @EventListener
    public void listener(EurekaInstanceRenewedEvent event) {
        log.info("服务续约{}：{} {}", event.isReplication(), event.getAppName(), event.getServerId());
    }

    @EventListener
    public void listener(EurekaInstanceCanceledEvent event) {
        log.info("服务下线{}：{} {}", event.isReplication(), event.getAppName(), event.getServerId());
    }

}
