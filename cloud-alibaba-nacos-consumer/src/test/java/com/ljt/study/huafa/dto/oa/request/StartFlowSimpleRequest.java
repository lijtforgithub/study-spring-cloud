package com.ljt.study.huafa.dto.oa.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.InputStream;
import java.util.Map;

/**
 * @author LiJingTang
 * @date 2023-06-10 18:58
 */
@Data
public class StartFlowSimpleRequest {

    /**
     * OA登录名
     */
    @NotBlank(message = "登录名不能为空")
    private String loginName;

    /**
     * 公司名
     */
    @NotBlank(message = "公司名不能为空")
    private String accountName;

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
    @NotNull(message = "流程表单数据不能为空")
    private FlowFormXml flowForm;

    /**
     * 流程附件
     */
    private Map<String, InputStream> flowFile;

}
