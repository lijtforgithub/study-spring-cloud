package com.ljt.study.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LiJingTang
 * @date 2021-05-12 10:21
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping
    public String index() {
        return "整合dashboard";
    }

}
