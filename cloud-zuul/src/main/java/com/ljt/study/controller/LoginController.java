package com.ljt.study.controller;

import com.ljt.study.shiro.ShiroUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LiJingTang
 * @date 2021-06-02 20:34
 */
@Slf4j
@RestController
@RequestMapping
public class LoginController {

    @GetMapping("/login")
    public String index() {
        log.debug("debug: 日志");
        ShiroUtils.login();
        log.info("info: 日志");
        return "登录";
    }

    @GetMapping("/logout")
    public String logout() {
        ShiroUtils.logout();
        return "登出";
    }

}
