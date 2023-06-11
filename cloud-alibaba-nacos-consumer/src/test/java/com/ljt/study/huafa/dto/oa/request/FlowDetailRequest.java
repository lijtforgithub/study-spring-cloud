package com.ljt.study.huafa.dto.oa.request;

import com.alibaba.fastjson.annotation.JSONField;
import com.ljt.study.huafa.dto.oa.OABaseRequest;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author LiJingTang
 * @date 2023-06-11 13:10
 */
@Data
public class FlowDetailRequest extends OABaseRequest {

    /**
     * 流程ID
     */
    @NotBlank(message = "流程ID不能为空")
    @JSONField(name = "FLOWID")
    private String flowId;

}
