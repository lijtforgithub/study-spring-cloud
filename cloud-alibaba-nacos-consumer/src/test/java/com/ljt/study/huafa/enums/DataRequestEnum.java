package com.ljt.study.huafa.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpMethod;

/**
 * @author LiJingTang
 * @date 2023-05-21 10:22
 */
@Getter
@AllArgsConstructor
public enum DataRequestEnum implements RequestEnum {

    LIST_AREA("/api/gfmd/getHmAreaList", "股份-区域主数据", HttpMethod.POST);

    private final String url;
    private final String desc;
    private final HttpMethod method;

}
