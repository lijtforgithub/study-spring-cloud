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

    ONLINE("/online", "上线", HttpMethod.POST),
    GET_CLIENT_DETAIL("/describe_client", "查看座席详情", HttpMethod.GET),
    LIST_CALL_RECORD("/list_cdr_all", "查询通话记录列表", HttpMethod.GET),
    GET_CALL_RECORD_URL("/describe_record_file_url", "查看通话录音地址", HttpMethod.GET);

    private final String url;
    private final String desc;
    private final HttpMethod method;

}
