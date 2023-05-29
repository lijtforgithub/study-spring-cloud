package com.ljt.study.huafa.enums;

import org.springframework.http.HttpMethod;

/**
 * @author LiJingTang
 * @date 2023-05-19 21:26
 */
public interface RequestEnum {

    String getUrl();
    String getDesc();
    HttpMethod getMethod();

}
