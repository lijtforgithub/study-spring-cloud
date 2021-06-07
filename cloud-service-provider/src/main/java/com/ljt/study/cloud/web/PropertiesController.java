package com.ljt.study.cloud.web;

import com.ljt.study.cloud.properties.TestProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LiJingTang
 * @date 2021-06-07 15:13
 */
@RefreshScope
@RestController
@RequestMapping("/prop")
public class PropertiesController {

    @Autowired
    private TestProperties testProperties;

    @GetMapping("/test")
    public TestProperties test() {
        return testProperties;
    }

}
