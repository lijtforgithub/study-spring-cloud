package com.ljt.study.huafa.client;

import cn.hutool.core.lang.Assert;
import com.ljt.study.huafa.dto.data.DataBaseRequest;
import com.ljt.study.huafa.dto.data.DataBaseResponse;
import com.ljt.study.huafa.enums.DataRequestEnum;
import com.ljt.study.huafa.enums.SystemEnum;
import com.ljt.study.huafa.prop.DataProperties;
import com.ljt.study.huafa.prop.HttpClientProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author LiJingTang
 * @date 2023-05-19 21:05
 */
@Slf4j
public class DataHttpClient extends BaseHttpClient<DataRequestEnum, DataBaseRequest, DataBaseResponse<?>> {

    @Autowired
    private DataProperties dataProperties;


    public <R extends DataBaseResponse<?>> R execute(DataRequestEnum requestEnum, DataBaseRequest req, Class<R> clazz) {
        return super.doExecute(requestEnum, req, clazz);
    }


    @Override
    protected String postUrl(String url) {
        String param = String.format("access_token=%s", dataProperties.getAccessToken());
        return url + (url.contains("?") ? "&" : "?") + param;
    }

    @Override
    protected void handleResponse(DataBaseResponse<?> resp) {
        super.handleResponse(resp);
        Assert.isTrue(200 == resp.getResultCode(), resp.getResultMsg());
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
