package com.ljt.study.websocket.socketio.registry;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.registry.NacosRegistration;
import com.ljt.study.websocket.socketio.SocketIoProperties;
import org.springframework.context.ApplicationContext;

/**
 * @author LiJingTang
 * @date 2022-04-25 9:16
 */
public class SocketIoRegistration extends NacosRegistration {

    private final SocketIoProperties socketIoProperties;

    public SocketIoRegistration(SocketIoProperties socketIoProperties, NacosDiscoveryProperties nacosDiscoveryProperties, ApplicationContext context) {
        super(null, nacosDiscoveryProperties, context);
        this.socketIoProperties = socketIoProperties;
    }

    @Override
    public String getServiceId() {
        return super.getServiceId() + socketIoProperties.getServiceSuffix();
    }

    @Override
    public int getPort() {
        return socketIoProperties.getPort();
    }

}
