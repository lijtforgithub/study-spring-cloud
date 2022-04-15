package com.ljt.study.websocket.stomp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author LiJingTang
 * @date 2022-03-11 10:10
 */
@Slf4j
@Controller
public class StompController {

    @Autowired
    private ServerProperties serverProperties;
    @Autowired
    private SimpMessagingTemplate template;

    @MessageMapping("/message")
    public String message(Message<String> message) {
        log.info("接收客户端消息 {}", message.getPayload());
        return "服务端推送消息：" + serverProperties.getPort();
    }

    @ResponseBody
    @GetMapping("/stomp/send")
    public String sendMsg(String msg) {
        try {
            String content = String.format("服务端主动推送消息：%s-%s", msg, serverProperties.getPort());
            template.convertAndSend("/topic/message", content);
            return content;
        } catch (MessageDeliveryException e) {
            e.printStackTrace();
            return null;
        }
    }
}

