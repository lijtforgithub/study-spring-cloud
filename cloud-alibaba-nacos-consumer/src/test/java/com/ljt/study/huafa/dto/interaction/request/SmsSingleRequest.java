package com.ljt.study.huafa.dto.interaction.request;

import com.ljt.study.huafa.dto.interaction.InteractionBaseRequest;
import lombok.Data;

/**
 * @author LiJingTang
 * @date 2023-05-22 09:16
 */
@Data
public class SmsSingleRequest extends InteractionBaseRequest {

    private String message;
    private String mobile;
    private String createId;
    private String templateNo;

}
