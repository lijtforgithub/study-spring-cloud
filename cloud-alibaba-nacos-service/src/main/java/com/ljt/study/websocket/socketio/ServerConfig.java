package com.ljt.study.websocket.socketio;

import com.corundumstudio.socketio.SocketConfig;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
import com.ljt.study.websocket.socketio.registry.SocketIoAutoRegistration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author LiJingTang
 * @date 2022-04-16 20:18
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(SocketIoProperties.class)
class ServerConfig {

    @Autowired
    private SocketIoProperties socketIoProperties;
    @Autowired
    private SocketIoAutoRegistration socketIoAutoRegistration;

    @Bean(destroyMethod = "stop")
    SocketIOServer server() {
        SocketConfig socketConfig = new SocketConfig();
        socketConfig.setTcpNoDelay(true);
        socketConfig.setSoLinger(0);

        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
        config.setSocketConfig(socketConfig);
        config.setPort(socketIoProperties.getPort());
        return new SocketIOServer(config);
    }

    @Bean
    SpringAnnotationScanner springAnnotationScanner(SocketIOServer server) {
        return new SpringAnnotationScanner(server);
    }

    @Bean
    public SmartInitializingSingleton registrySocketIo() {
        return () -> {
            server().start();
            socketIoAutoRegistration.start();
        };
    }

}
