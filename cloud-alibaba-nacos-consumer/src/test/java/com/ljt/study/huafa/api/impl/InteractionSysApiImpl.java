package com.ljt.study.huafa.api.impl;

import com.ljt.study.huafa.api.InteractionSysApi;
import com.ljt.study.huafa.client.InteractionHttpClient;
import com.ljt.study.huafa.dto.interaction.request.SmsSingleRequest;
import com.ljt.study.huafa.dto.interaction.response.SmsSingleResponse;
import lombok.RequiredArgsConstructor;

import static com.ljt.study.huafa.enums.InteractionRequestEnum.SMS_SINGLE;

/**
 * @author LiJingTang
 * @date 2023-05-22 09:22
 */
@RequiredArgsConstructor
public class InteractionSysApiImpl implements InteractionSysApi {

    private final InteractionHttpClient client;

    @Override
    public SmsSingleResponse smsSendSingle(SmsSingleRequest request) {
        return client.execute(SMS_SINGLE, request, SmsSingleResponse.class);
    }

}
