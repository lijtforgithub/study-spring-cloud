package com.ljt.study.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LiJingTang
 * @date 2021-06-26 13:22
 */
@Slf4j
@RestController
@RequestMapping("/shiro")
public class ShiroController {

    @GetMapping("/user")
    @RequiresRoles("user")
    public String getUser() {
        log.info("U");

        getUser2();

        return "user";
    }

    @GetMapping("/user2")
    @RequiresRoles("user")
    public String getUser2() {
        log.info("U2");

        return "user2";
    }

    @GetMapping("/admin")
    @RequiresRoles("admin")
    public String getAdmin() {
        log.info("A");
        return "admin";
    }

}
