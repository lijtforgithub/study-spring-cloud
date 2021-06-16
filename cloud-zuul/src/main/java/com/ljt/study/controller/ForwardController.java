package com.ljt.study.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LiJingTang
 * @date 2021-05-31 20:01
 */
@RestController
@RequestMapping("/forward")
public class ForwardController {

    @GetMapping("/index")
    public String index() {
        return "SendForwardFilter 使用";
    }

}
