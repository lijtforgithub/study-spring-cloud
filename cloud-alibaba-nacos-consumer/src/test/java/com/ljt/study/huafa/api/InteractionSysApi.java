package com.ljt.study.huafa.api;

import com.ljt.study.huafa.dto.interaction.request.SmsSingleRequest;

/**
 * @author LiJingTang
 * @date 2023-05-22 09:15
 */
public interface InteractionSysApi {

    /**
     * 发送单条短信
     *
     * @return 发送状态 SENDING-发送中
     */
    String sendSingleSms(SmsSingleRequest request);

    /**
     * 查询短信发送状态
     *
     * @param appSerialNo 序列化 发送短信时传入
     * @return 发送状态
     */
    String getSmsStatus(String appSerialNo);

}
