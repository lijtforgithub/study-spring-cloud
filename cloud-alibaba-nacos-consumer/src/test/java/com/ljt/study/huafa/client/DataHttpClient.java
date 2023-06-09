package com.ljt.study.huafa.client;

import cn.hutool.core.lang.Assert;
import com.ljt.study.huafa.dto.data.DataBaseRequest;
import com.ljt.study.huafa.dto.data.DataBaseResponse;
import com.ljt.study.huafa.enums.DataRequestEnum;
import com.ljt.study.huafa.enums.SystemEnum;
import com.ljt.study.huafa.exception.ClientException;
import com.ljt.study.huafa.prop.DataProperties;
import com.ljt.study.huafa.prop.HttpClientProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;

import java.net.URI;

/**
 * @author LiJingTang
 * @date 2023-05-19 21:05
 */
@Slf4j
public class DataHttpClient extends BaseHttpClient<DataRequestEnum, DataBaseRequest, DataBaseResponse<?>> {

    private static final String ACCESS_TOKEN = "access_token";

    @Autowired
    private DataProperties dataProperties;


    public <R extends DataBaseResponse<?>> R execute(DataRequestEnum requestEnum, DataBaseRequest req, Class<R> clazz) {
        return super.doExecute(requestEnum, req, clazz);
    }


    @Override
    protected URI processQueryParam(DataRequestEnum requestEnum, MultiValueMap<String, String> query) {
        query.addIfAbsent(ACCESS_TOKEN, dataProperties.getAccessToken());
        return super.processQueryParam(requestEnum, query);
    }

    @Override
    protected void handleResponse(DataBaseResponse<?> resp) {
        super.handleResponse(resp);
        Assert.isTrue(200 == resp.getResultCode(), () -> new ClientException(resp.getResultMsg()));
    }

    @Override
    protected String getBaseUrl() {
        return dataProperties.getUrl();
    }

    @Override
    protected SystemEnum getSystem() {
        return SystemEnum.DATA;
    }

    @Override
    protected HttpClientProperties getHttpClientProperties() {
        return dataProperties.getHttpclient();
    }

}
