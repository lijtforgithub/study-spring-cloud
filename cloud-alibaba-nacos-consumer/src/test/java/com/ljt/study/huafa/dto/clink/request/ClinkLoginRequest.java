package com.ljt.study.huafa.dto.clink.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author LiJingTang
 * @date 2023-06-09 15:47
 */
@Data
public class ClinkLoginRequest {

    /**
     * 用户ID
     */
    @NotBlank(message = "用户ID不能为空")
    private String uid;

    /**
     * 用户名称
     */
    @NotBlank(message = "用户名称不能为空")
    private String username;

}
