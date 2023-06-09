package com.ljt.study.huafa.dto.interaction.request;

import com.ljt.study.huafa.dto.QueryParam;
import com.ljt.study.huafa.dto.interaction.InteractionBaseRequest;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author LiJingTang
 * @date 2023-06-01 16:55
 */
@Data
public class SmsQueryRequest extends InteractionBaseRequest {

    @QueryParam
    @NotBlank(message = "短信流水号不能为空")
    private String appSerialNo;

}
