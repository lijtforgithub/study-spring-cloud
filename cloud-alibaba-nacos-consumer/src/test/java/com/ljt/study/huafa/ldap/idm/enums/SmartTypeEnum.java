package com.ljt.study.huafa.ldap.idm.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author LiJingTang
 * @date 2023-05-25 15:44
 */
@Getter
@AllArgsConstructor
public enum SmartTypeEnum {

    ALL("*", "所有用户"),
    E1("E1", "内部用户"),
    E2("E2", "外部用户"),
    P1("P1", "公共账号");

    private final String value;
    private final String desc;

}
