package com.ljt.study.api.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author LiJingTang
 * @date 2021-08-04 15:02
 */
@Data
@Builder
public class UserDTO implements Serializable {

    private Integer id;
    private String name;
    private Integer gender;
    private String account;
    private String address;

}
