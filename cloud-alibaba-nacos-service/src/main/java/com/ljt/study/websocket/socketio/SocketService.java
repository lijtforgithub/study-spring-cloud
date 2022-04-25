package com.ljt.study.websocket.socketio;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LiJingTang
 * @date 2022-04-16 20:32
 */
@Slf4j
@RestController
public class SocketService {

    public static final String CHAT_EVENT = "chat.event";

    @Autowired
    private SocketIOServer server;
    @Autowired
    private SocketIoProperties socketIoProperties;

    @OnEvent("chat.event")
    void onEvent(SocketIOClient client, String data, AckRequest ackRequest) {
        log.info("接收客户端消息：{} => {}", client.getSessionId(), data);
        client.sendEvent(CHAT_EVENT, "服务端回复消息" + (socketIoProperties.getPort()));
    }

    @ResponseBody
    @GetMapping("/socket-io/send")
    public String sendMsg(String msg) {
        msg = (socketIoProperties.getPort()) + "广播消息" + msg;
        server.getBroadcastOperations().sendEvent(CHAT_EVENT, msg);
        log.info(msg);
        return msg;
    }

}
