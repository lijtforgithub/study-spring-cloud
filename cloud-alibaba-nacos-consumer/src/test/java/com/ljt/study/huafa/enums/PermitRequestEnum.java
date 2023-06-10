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

    LIST_USER_POSITION("/api/v2/getPositionByClient", "用户岗位（按客户端）", HttpMethod.GET),
    LIST_USER_PROJECT("/api/v2/getUserProjectByParam", "用户项目（多条件）", HttpMethod.GET),
    LIST_USER_MENU("/api/v2/getUserClientPositionLeftMenu", "用户左侧菜单（按客户端和岗位）", HttpMethod.GET),
    LIST_USER_PERMISSION("/api/v2/getUserMenuPermissionByParam", "用户菜单权限（多条件）", HttpMethod.GET);

    private final String url;
    private final String desc;
    private final HttpMethod method;

}
