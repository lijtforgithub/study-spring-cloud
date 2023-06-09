package com.ljt.study.huafa.dto;

import lombok.Data;

/**
 * @author LiJingTang
 * @date 2023-05-24 16:48
 */
@Data
public class GatewayBaseResponse<T> {

    private String code;
    private String msg;

    private T data;

}
