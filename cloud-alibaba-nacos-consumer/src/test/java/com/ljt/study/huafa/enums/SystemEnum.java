package com.ljt.study.huafa.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author LiJingTang
 * @date 2023-05-22 08:47
 */
@Getter
@AllArgsConstructor
public enum SystemEnum {

    IDM("sys-idm", "IDM系统"),
    MPM("sys-mpm", "华发+"),
    OA("sys-oa", "OA系统"),
    DATA("sys-data", "数仓系统"),
    PERMIT("sys-permit", "权限系统"),
    CUSTOMER("sys-customer", "客户中心"),
    CLINK("sys-clink", "呼叫中心"),
    INTERACTION("sys-interaction", "交互中心");

    private final String name;
    private final String desc;

}
