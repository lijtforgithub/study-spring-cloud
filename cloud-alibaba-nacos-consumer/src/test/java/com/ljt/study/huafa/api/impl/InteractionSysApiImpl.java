package com.ljt.study.huafa.api.impl;

import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ljt.study.huafa.api.InteractionSysApi;
import com.ljt.study.huafa.client.InteractionHttpClient;
import com.ljt.study.huafa.dto.interaction.request.SmsQueryRequest;
import com.ljt.study.huafa.dto.interaction.request.SmsSingleRequest;
import com.ljt.study.huafa.dto.interaction.response.SmsQueryResponse;
import com.ljt.study.huafa.dto.interaction.response.SmsSingleResponse;
import com.ljt.study.huafa.exception.ClientException;
import lombok.RequiredArgsConstructor;

import static com.ljt.study.huafa.enums.InteractionRequestEnum.SMS_QUERY;
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

    @Override
    public String smsQueryStatus(String appSerialNo) {
        SmsQueryRequest request = new SmsQueryRequest();
        request.setAppSerialNo(appSerialNo);
        SmsQueryResponse response = client.execute(SMS_QUERY, request, SmsQueryResponse.class);
        String data = response.getData();
        Assert.isFalse("null".equals(data), () -> new ClientException(response.getMsg()));

        JSONObject obj = JSON.parseObject(data);
        return obj.getString("status");
    }

}
