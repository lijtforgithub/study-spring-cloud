package com.ljt.study.cloud.web;

import com.ljt.study.cloud.service.CustomHealthStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LiJingTang
 * @date 2020-06-23 14:28
 */
@RestController
@RequestMapping("/health")
public class HealthStatusController {

    @Autowired
    private CustomHealthStatus healthStatus;

    @GetMapping()
    public void up(@RequestParam Boolean up) {
        healthStatus.setUp(up);
    }

}
