package com.ljt.study.huafa.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpMethod;

/**
 * @author LiJingTang
 * @date 2023-05-22 15:48
 */
@Getter
@AllArgsConstructor
public enum PermitRequestEnum implements RequestEnum {

    LIST_USER_POSITION("/api/user/v2/getUserPositionList", "用户列表（有岗位的）", HttpMethod.POST),
    LIST_USER_PROJECT("/api/v2/getUserProjectByParam", "用户项目（多条件））", HttpMethod.GET);

    private final String url;
    private final String desc;
    private final HttpMethod method;

}
