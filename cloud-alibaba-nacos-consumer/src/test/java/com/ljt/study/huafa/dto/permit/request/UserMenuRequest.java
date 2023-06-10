package com.ljt.study.huafa.dto.permit.request;

import com.ljt.study.huafa.dto.permit.PermitBaseRequest;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author LiJingTang
 * @date 2023-06-10 17:17
 */
@Data
public class UserMenuRequest extends PermitBaseRequest {

    /**
     * 客户端编码
     */
    @NotBlank(message = "客户端编码不能为空")
    private String clientCode;

    /**
     * 	岗位ID
     */
    @NotBlank(message = "岗位ID不能为空")
    private String positionId;

    /**
     * 	用户ID
     */
    @NotBlank(message = "用户ID不能为空")
    private String userId;

}
