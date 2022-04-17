package com.ljt.study.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LiJingTang
 * @date 2022-04-17 13:09
 */
@RestController
@RequestMapping("/fw")
public class ForwardController {

    @GetMapping("/baidu")
    public String baidu() {
        return "百度";
    }

}
