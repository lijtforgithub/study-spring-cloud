package com.ljt.study.huafa.dto.clink.request;

import com.ljt.study.huafa.dto.clink.ClinkBaseRequest;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author LiJingTang
 * @date 2023-06-09 15:10
 */
@Data
public class ClientDetailRequest extends ClinkBaseRequest {

    /**
     * 座席号
     */
    private String cno;

    /**
     * 账号名称
     */
    @NotBlank(message = "账号名称不能为空")
    private String username;

}
