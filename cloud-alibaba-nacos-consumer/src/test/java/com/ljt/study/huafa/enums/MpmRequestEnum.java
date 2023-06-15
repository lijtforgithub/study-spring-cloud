package com.ljt.study.huafa.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpMethod;

/**
 * @author LiJingTang
 * @date 2023-06-15 14:20
 */
@Getter
@AllArgsConstructor
public enum MpmRequestEnum implements RequestEnum {

    PUSH_TEXT("/mpm/v5/open/textmsg/push", "文本消息推送", HttpMethod.POST);

    private final String url;
    private final String desc;
    private final HttpMethod method;

}
