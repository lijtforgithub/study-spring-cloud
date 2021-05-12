package com.ljt.study.cloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.ljt.study.cloud.config.SentinelConfig.RS_HW;

/**
 * 注解使用方式
 *
 * @author LiJingTang
 * @date 2021-05-11 19:23
 */
@RestController
@RequestMapping("/rs")
public class ResourceController {

    @SentinelResource(value = RS_HW, blockHandler = "back")
    @GetMapping()
    public String index() {
        return "Sentinel限流：一个资源";
    }

    public String back(BlockException e) {
        return "资源限流降级了";
    }

}
