package com.ljt.study.controller;

import com.ljt.study.dao.TestMapper;
import com.ljt.study.entity.Test;
import com.ljt.study.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LiJingTang
 * @date 2023-04-13 10:59
 */
@RestController
@RequestMapping("/feign")
public class FeignController {

    @Autowired
    private TestService testService;

    @PostMapping("/saveT")
    public Integer saveT(String key) {
        return testService.save(key);
    }

}
