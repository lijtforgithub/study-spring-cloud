package com.ljt.study.websocket.socketio;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Optional;

/**
 * @author LiJingTang
 * @date 2022-04-25 9:21
 */
@Data
@ConfigurationProperties(prefix = "socket.io")
public class SocketIoProperties {

    @Autowired
    private ServerProperties serverProperties;

    private Integer port;

    private String serviceSuffix = "-socketIo";

    public Integer getPort() {
        return Optional.ofNullable(port).orElseGet(() -> serverProperties.getPort() + 10);
    }
}
