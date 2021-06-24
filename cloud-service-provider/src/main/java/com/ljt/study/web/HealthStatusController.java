package com.ljt.study.sse;

import com.ljt.study.service.CustomHealthStatusService;
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
    private CustomHealthStatusService customHealthStatusService;

    @GetMapping()
    public void up(@RequestParam Boolean up) {
        customHealthStatusService.setUp(up);
    }

}
