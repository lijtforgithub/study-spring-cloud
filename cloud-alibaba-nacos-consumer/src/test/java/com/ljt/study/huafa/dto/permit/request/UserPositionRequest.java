package com.ljt.study.huafa.dto.permit.request;

import com.ljt.study.huafa.dto.permit.PermitBaseRequest;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author LiJingTang
 * @date 2023-05-22 15:53
 */
@Data
public class UserPositionRequest extends PermitBaseRequest {

    /**
     * 客户端编码
     */
    @NotBlank(message = "客户端编码不能为空")
    private String clientCode;

    /**
     * 用户ID
     */
    @NotBlank(message = "用户ID不能为空")
    private String userId;

}
