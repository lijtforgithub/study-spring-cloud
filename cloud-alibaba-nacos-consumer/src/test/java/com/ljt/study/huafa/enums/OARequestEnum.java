package com.ljt.study.huafa.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpMethod;

/**
 * @author LiJingTang
 * @date 2023-05-23 19:02
 */
@Getter
@AllArgsConstructor
public enum OARequestEnum implements RequestEnum {

    START_FLOW("/RESTAdapter/EXT/OA/FlowStart", "发起流程", HttpMethod.POST),
    GET_FLOW_DETAIL("/RESTAdapter/EXT/OA/FlowGetDetail", "获取流程详情", HttpMethod.POST);

    private final String url;
    private final String desc;
    private final HttpMethod method;

}
