package com.ljt.study.inteceptor;

import lombok.Data;

import java.io.Serializable;

/**
 * @author LiJingTang
 * @date 2022-01-07 9:05
 */
@Data
public class RequestDTO implements Serializable {

    private static final long serialVersionUID = -3001557465081927480L;

    private Long id;
    private String name;
    
}
