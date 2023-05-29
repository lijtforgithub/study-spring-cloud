package com.ljt.study.huafa.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpMethod;

/**
 * @author LiJingTang
 * @date 2023-05-22 08:44
 */
@Getter
@AllArgsConstructor
public enum InteractionRequestEnum implements RequestEnum {

    SMS_SINGLE("/message/singleSend", "单条短信", HttpMethod.POST);

    private final String url;
    private final String desc;
    private final HttpMethod method;

}
