package com.ljt.study.controller;

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
@RequestMapping("/login")
public class LoginController {

    @GetMapping
    public String index() {
        log.debug("debug: 日志");
        log.info("info: 日志");
        return "登录";
    }

}
