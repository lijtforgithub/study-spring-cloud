package com.ljt.study.websocket.socketio.registry;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.registry.NacosAutoServiceRegistration;
import com.alibaba.cloud.nacos.registry.NacosServiceRegistry;
import com.ljt.study.websocket.socketio.SocketIoProperties;
import org.springframework.cloud.client.serviceregistry.AutoServiceRegistrationProperties;
import org.springframework.context.ApplicationContext;

import javax.annotation.PreDestroy;
import java.util.Objects;

/**
 * @author LiJingTang
 * @date 2022-04-25 10:36
 */
public class SocketIoAutoRegistration {

    private final ApplicationContext applicationContext;
    private final SocketIoProperties socketIoProperties;
    private final AutoServiceRegistrationProperties autoServiceRegistrationProperties;
    private final NacosServiceRegistry nacosServiceRegistry;
    private final NacosDiscoveryProperties nacosDiscoveryProperties;

    public SocketIoAutoRegistration(ApplicationContext applicationContext, SocketIoProperties socketIoProperties,
                                    AutoServiceRegistrationProperties autoServiceRegistrationProperties,
                                    NacosServiceRegistry nacosServiceRegistry, NacosDiscoveryProperties nacosDiscoveryProperties) {
        this.applicationContext = applicationContext;
        this.socketIoProperties = socketIoProperties;
        this.autoServiceRegistrationProperties = autoServiceRegistrationProperties;
        this.nacosServiceRegistry = nacosServiceRegistry;
        this.nacosDiscoveryProperties = nacosDiscoveryProperties;
    }

    private NacosAutoServiceRegistration autoServiceRegistration;

    public void start() {
        SocketIoRegistration registration = new SocketIoRegistration(socketIoProperties, nacosDiscoveryProperties, applicationContext);
        autoServiceRegistration = new NacosAutoServiceRegistration(nacosServiceRegistry, autoServiceRegistrationProperties, registration);
        autoServiceRegistration.setApplicationContext(applicationContext);
        autoServiceRegistration.start();
    }

    @PreDestroy
    public void destroy() {
        if (Objects.nonNull(autoServiceRegistration)) {
            autoServiceRegistration.stop();
        }
    }

}
