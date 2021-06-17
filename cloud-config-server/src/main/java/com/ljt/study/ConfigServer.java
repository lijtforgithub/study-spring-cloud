package com.ljt.study;

import com.ljt.study.properties.PriorityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 配置中心服务端
 *
 * @author LiJingTang
 * @date 2020-06-23 10:45
 */
@Slf4j
@RestController
@SpringBootApplication
@EnableConfigServer
public class ConfigServer {

    public static void main(String[] args) {
        args = new String[] {"--priority.arg=启动参数", "--startArg=启动参数"};
        SpringApplication.run(ConfigServer.class, args);
    }

    @Autowired
    private PriorityProperties priority;
    @Value("${startArg}")
    private String startArg;

    @GetMapping("/priority")
    public PriorityProperties priority() {
        System.out.println(startArg);
        return priority;
    }

}