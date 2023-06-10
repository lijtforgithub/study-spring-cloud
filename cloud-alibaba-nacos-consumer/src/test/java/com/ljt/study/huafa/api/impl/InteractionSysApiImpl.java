package com.ljt.study.huafa.api.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ljt.study.huafa.api.InteractionSysApi;
import com.ljt.study.huafa.client.InteractionHttpClient;
import com.ljt.study.huafa.dto.interaction.request.SmsSingleRequest;
import com.ljt.study.huafa.dto.interaction.request.SmsStatusRequest;
import com.ljt.study.huafa.dto.interaction.response.SmsSingleResponse;
import com.ljt.study.huafa.dto.interaction.response.SmsStatusResponse;
import com.ljt.study.huafa.exception.ClientException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static com.ljt.study.huafa.enums.InteractionRequestEnum.GET_SMS_STATUS;
import static com.ljt.study.huafa.enums.InteractionRequestEnum.SEND_SINGLE_SMS;

/**
 * @author LiJingTang
 * @date 2023-05-22 09:22
 */
@Slf4j
@RequiredArgsConstructor
public class InteractionSysApiImpl implements InteractionSysApi {

    private final InteractionHttpClient client;

    @Override
    public String sendSingleSms(SmsSingleRequest request) {
        SmsSingleResponse response = client.execute(SEND_SINGLE_SMS, request, SmsSingleResponse.class);
        Assert.isTrue(StrUtil.isBlank(response.getMsg()), () -> {
            log.warn("{} => {}", response.getMsg(), response.getData());
            return new ClientException(response.getMsg());
        });

        return handData(response.getData());
    }

    @Override
    public String getSmsStatus(String appSerialNo) {
        SmsStatusRequest request = new SmsStatusRequest();
        request.setAppSerialNo(appSerialNo);
        SmsStatusResponse response = client.execute(GET_SMS_STATUS, request, SmsStatusResponse.class);

        return handData(response.getData());
    }

    private String handData(String data) {
        if (StrUtil.isBlank(data) || "null".equals(data)) {
            return null;
        }

        JSONObject obj = JSON.parseObject(data);
        return obj.getString("status");
    }

}
