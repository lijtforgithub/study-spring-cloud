package com.ljt.study.inteceptor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpMethod;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

/**
 * @author LiJingTang
 * @date 2022-01-04 11:20
 */
@Getter
@AllArgsConstructor
public enum ApiPathEnum {

    /**
     * 请求
     */
    GET_TOKEN(POST, "/auth/oauth/token", "获取Token"),

    QUERY_BY_SOURCE_CLASS_LABEL(GET, "/know-center/queryBysourceClassAndLabel", "通过关键词和标签检索内容"),
    ASSOCIATION_WORD(GET, "/know-center/know/associationWord", "医疗词汇联想功能接口");

    /**
     * 请求方式
     */
    private final HttpMethod method;
    /**
     * 相对路径
     */
    private final String path;
    /**
     * 接口描述
     */
    private final String desc;

}
