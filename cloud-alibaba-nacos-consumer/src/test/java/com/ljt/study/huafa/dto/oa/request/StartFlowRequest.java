package com.ljt.study.huafa.dto.oa.request;

import com.ljt.study.huafa.dto.oa.OABaseRequest;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author LiJingTang
 * @date 2023-05-23 19:07
 */
@Data
public class StartFlowRequest extends OABaseRequest {

    /**
     * 来源系统名
     */
    @NotBlank(message = "系统名不能为空")
    private String systemName;

    /**
     * OA登录名
     */
    @NotBlank(message = "登录名不能为空")
    private String loginName;

    /**
     * 验证串 约定密匙 ：如sap系统为  md5转的”sap+loginName
     */
    @NotBlank(message = "验证串不能为空")
    private String authCode;

    /**
     * 流程名
     */
    @NotBlank(message = "流程名不能为空")
    private String subject;

    /**
     * 流程编码
     */
     @NotBlank(message = "流程编码不能为空")
    private String flowCode;

    /**
     * 流程表单数据
     */
    @NotBlank(message = "流程表单数据不能为空")
    private String datas;

    /**
     * 流程附件
     */
    private String files;

    /**
     * 公司名
     */
    @NotBlank(message = "公司名不能为空")
    private String accountName;

    /**
     * 发送状态，0，流程直接发起  1，放入发起人待发事项
     */
    @NotBlank(message = "发送状态不能为空")
    private String sendState;

    /**
     * 接口版本
     */
    @NotBlank(message = "接口版本不能为空")
    private String version;

}
