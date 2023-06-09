package com.ljt.study.huafa.api;

import com.ljt.study.huafa.dto.interaction.request.SmsSingleRequest;
import com.ljt.study.huafa.dto.interaction.response.SmsSingleResponse;

/**
 * @author LiJingTang
 * @date 2023-05-22 09:15
 */
public interface InteractionSysApi {

    SmsSingleResponse sendSingleSms(SmsSingleRequest request);

    String getSmsStatus(String appSerialNo);

}
