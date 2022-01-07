package com.ljt.study.inteceptor;

import lombok.Data;

import java.io.Serializable;

/**
 * @author LiJingTang
 * @date 2022-01-07 9:06
 */
@Data
public class ResponseDTO implements Serializable {

    private static final long serialVersionUID = 7107382418841790732L;

    private int code;
    private String message;

}
