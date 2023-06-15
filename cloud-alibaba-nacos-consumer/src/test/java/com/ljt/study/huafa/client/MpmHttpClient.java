package com.ljt.study.huafa.client;

import cn.hutool.crypto.digest.HMac;
import cn.hutool.crypto.digest.HmacAlgorithm;
import com.ljt.study.huafa.dto.mpm.MpmBaseRequest;
import com.ljt.study.huafa.dto.mpm.MpmBaseResponse;
import com.ljt.study.huafa.enums.RequestEnum;
import com.ljt.study.huafa.enums.SystemEnum;
import com.ljt.study.huafa.prop.HttpClientProperties;
import com.ljt.study.huafa.prop.MpmProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;

import java.sql.Timestamp;

/**
 * @author LiJingTang
 * @date 2023-06-15 14:22
 */
public class MpmHttpClient extends BaseHttpClient<RequestEnum, MpmBaseRequest, MpmBaseResponse> {

    private static final String TIMESTAMP = "timestamp";
    private static final String SYS_KEY = "sysKey";
    private static final String SIGN = "sign";

    @Autowired
    private MpmProperties mpmProperties;

    public <R extends MpmBaseResponse> R execute(RequestEnum requestEnum, MpmBaseRequest req, Class<R> clazz) {
        return super.doExecute(requestEnum, req, clazz);
    }


    @Override
    protected HttpHeaders getHttpHeader() {
        HttpHeaders headers = new HttpHeaders();
        String timestamp = new Timestamp(System.currentTimeMillis()).toString();
        headers.add(TIMESTAMP, timestamp);
        headers.add(SYS_KEY, mpmProperties.getSysKey());
        headers.add(SIGN, createSign(timestamp));
        return headers;
    }

    @Override
    protected String getBaseUrl() {
        return mpmProperties.getUrl();
    }

    @Override
    protected SystemEnum getSystem() {
        return SystemEnum.MPM;
    }

    @Override
    protected HttpClientProperties getHttpClientProperties() {
        return mpmProperties.getHttpclient();
    }

    private String createSign(String timestamp) {
        byte[] key = mpmProperties.getSecret().getBytes();
        HMac mac = new HMac(HmacAlgorithm.HmacSHA1, key);
        String param = String.format("%s=%s&%s=%s", SYS_KEY, mpmProperties.getSysKey(), TIMESTAMP, timestamp);
        return mac.digestHex(param);
    }

}
