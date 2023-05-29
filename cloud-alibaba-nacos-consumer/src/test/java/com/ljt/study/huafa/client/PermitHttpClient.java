package com.ljt.study.huafa.client;

import com.ljt.study.huafa.dto.permit.PermitBaseRequest;
import com.ljt.study.huafa.dto.permit.PermitBaseResponse;
import com.ljt.study.huafa.enums.PermitRequestEnum;
import com.ljt.study.huafa.enums.SystemEnum;
import com.ljt.study.huafa.prop.HttpClientProperties;
import com.ljt.study.huafa.prop.PermitProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author LiJingTang
 * @date 2023-05-22 16:00
 */
@Slf4j
public class PermitHttpClient extends GatewayHttpClient<PermitRequestEnum, PermitBaseRequest, PermitBaseResponse<?>> {

    @Autowired
    private PermitProperties permitProperties;


    public <R extends PermitBaseResponse<?>> R execute(PermitRequestEnum requestEnum, PermitBaseRequest req, Class<R> clazz) {
        return super.doExecute(requestEnum, req, clazz);
    }


    @Override
    protected String getBaseUrl() {
        return permitProperties.getUrl();
    }

    @Override
    protected SystemEnum getSystem() {
        return SystemEnum.PERMIT;
    }

    @Override
    protected HttpClientProperties getHttpClientProperties() {
        return permitProperties.getHttpclient();
    }

}
