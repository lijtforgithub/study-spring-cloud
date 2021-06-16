package com.ljt.study.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author LiJingTang
 * @date 2020-07-01 10:31
 */
@RequestMapping("/api")
public interface ServiceApi {

    String APP_NAME = "service-provider";

    /**
     * 查询当前时间
     *
     * @return 当前时间
     */
    @GetMapping("/time")
    String getCurrentTime();

    /**
     * 查询实例端口号
     *
     * @return 端口号
     */
    @GetMapping("/port")
    String getServicePort();

}
