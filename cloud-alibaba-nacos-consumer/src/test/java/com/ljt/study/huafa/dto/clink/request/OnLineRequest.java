package com.ljt.study.huafa.dto.clink.request;

import com.ljt.study.huafa.dto.clink.ClinkBaseRequest;
import lombok.Data;

import javax.validation.constraints.*;

/**
 * @author LiJingTang
 * @date 2023-06-10 12:21
 */
@Data
public class OnLineRequest extends ClinkBaseRequest {

    /**
     * IDM用户账号
     */
    @NotBlank(message = "用户账号不能为空")
    private String username;

    /**
     * 电话类型，1:电话；2:IP话机
     */
    @NotNull(message = "电话类型不能为空")
    @Min(value = 1, message = "电话类型不正确")
    @Max(value = 2, message = "电话类型不正确")
    private Integer bindType;

    /**
     * 绑定电话
     */
    @NotBlank(message = "绑定电话不能为空")
    private String bindTel;

    /**
     * 登录状态，1:空闲；2:置忙，默认值为1
     */
    private Integer status;

    /**
     * 业务线
     */
    @NotBlank(message = "业务线标识不能为空")
    private String businessLine;

    /**
     * 平台
     */
    @NotBlank(message = "平台编号不能为空")
    private String platform;

    /**
     * 状态描述，当status为2时需要给定参数值，描述需包含在企业自定义的置忙状态内
     */
    private String description;

}
