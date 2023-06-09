package com.ljt.study.huafa.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpMethod;

/**
 * @author LiJingTang
 * @date 2023-06-06 11:43
 */
@Getter
@AllArgsConstructor
public enum ClinkRequestEnum implements RequestEnum {

    GET_ALL_CDR("/list_cdr_all", "查询通话记录列表", HttpMethod.GET);

    private final String url;
    private final String desc;
    private final HttpMethod method;

}
