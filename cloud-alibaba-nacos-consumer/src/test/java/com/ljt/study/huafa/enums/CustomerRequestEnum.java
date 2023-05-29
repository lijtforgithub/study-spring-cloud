package com.ljt.study.huafa.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpMethod;

/**
 * @author LiJingTang
 * @date 2023-05-19 21:27
 */
@Getter
@AllArgsConstructor
public enum CustomerRequestEnum implements RequestEnum {

    GET_INCREMENT_OWNER_INFO("/ownerInfo/getIncrementOwnerInfo", "查询增量业主", HttpMethod.POST),
    GET_OWNER_LIST("/ownerInfo/getOwnerList", "查询增量业主(改良后)", HttpMethod.POST),
    GET_OWNER_INFO("/ownerInfo/getOwnerInfo", "查询业主信息", HttpMethod.POST);

    private final String url;
    private final String desc;
    private final HttpMethod method;

}
