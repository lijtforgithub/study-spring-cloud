package com.ljt.study.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author LiJingTang
 * @date 2021-08-04 15:02
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 6757923320508188629L;

    private Integer id;
    private String name;
    private Integer gender;
    private String account;
    private String address;

}
