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

    GET_DICT_CONFIG("/dict/getCertificateConfig", "查询证件类型", HttpMethod.POST),
    GET_INCR_OWNER_INFO("/ownerInfo/getIncrementOwnerInfo", "查询增量业主", HttpMethod.POST),
    GET_INCR_OWNER_ROOM("/ownerInfo/getIncrementOwner", "查询增量业主房客关系", HttpMethod.POST);

    private final String url;
    private final String desc;
    private final HttpMethod method;

}
