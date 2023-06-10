package com.ljt.study.huafa.dto.permit.request;

import com.ljt.study.huafa.dto.permit.PermitBaseRequest;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author LiJingTang
 * @date 2023-06-10 17:17
 */
@Data
public class UserPermissionRequest extends PermitBaseRequest {

    /**
     * 	用户ID
     */
    @NotBlank(message = "用户ID不能为空")
    private String userId;

    /**
     * 客户端编码
     */
    private String clientCode;

    /**
     * 菜单ID
     */
    private String menuId;

    /**
     * 	岗位ID
     */
    private String positionId;

}
