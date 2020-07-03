package com.ljt.study.cloud;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author LiJingTang
 * @date 2020-07-03 12:01
 */
@EnableAdminServer
@SpringBootApplication
public class AdminServer {

    public static void main(String[] args) {
        SpringApplication.run(AdminServer.class);
    }

}
