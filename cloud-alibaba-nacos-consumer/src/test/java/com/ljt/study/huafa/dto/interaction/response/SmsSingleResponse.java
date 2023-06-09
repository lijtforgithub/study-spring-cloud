package com.ljt.study.huafa.dto.interaction.response;

import com.ljt.study.huafa.dto.interaction.InteractionBaseResponse;
import lombok.Data;

/**
 * @author LiJingTang
 * @date 2023-05-22 09:20
 */
@Data
public class SmsSingleResponse extends InteractionBaseResponse<Object> {

    private String message;
    /**
     * SENDING 发送中
     */
    private String status;

}
