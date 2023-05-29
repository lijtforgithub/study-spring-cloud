package com.ljt.study.huafa.client;

import cn.hutool.core.lang.Assert;
import cn.hutool.crypto.digest.HMac;
import cn.hutool.crypto.digest.HmacAlgorithm;
import com.ljt.study.huafa.dto.GatewayBaseResponse;
import com.ljt.study.huafa.enums.RequestEnum;
import com.ljt.study.huafa.prop.GatewayProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;

import java.sql.Timestamp;

/**
 * @author LiJingTang
 * @date 2023-05-22 09:37
 */
abstract class GatewayHttpClient<E extends RequestEnum, T, R extends GatewayBaseResponse<?>> extends BaseHttpClient<E, T, R> {

    @Autowired
    private GatewayProperties gatewayProperties;

    @Override
    protected HttpHeaders getHttpHeader() {
        HttpHeaders headers = super.getHttpHeader();
        String timestamp = new Timestamp(System.currentTimeMillis()).toString();
        headers.add("timestamp", timestamp);
        headers.add("appid", gatewayProperties.getAppId());
        headers.add("sign", createSign(timestamp));
        return headers;
    }

    @Override
    protected void handleResponse(R resp) {
        super.handleResponse(resp);
        Assert.isTrue("200".equals(resp.getCode()), resp.getMsg());
    }

    private String createSign(String timestamp) {
        byte[] key = gatewayProperties.getAppSecret().getBytes();
        HMac mac = new HMac(HmacAlgorithm.HmacSHA1, key);
        String param = String.format("appid=%s&timestamp=%s", gatewayProperties.getAppId(), timestamp);
        return mac.digestHex(param);
    }

}
