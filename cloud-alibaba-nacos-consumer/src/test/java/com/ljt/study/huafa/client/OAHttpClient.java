package com.ljt.study.huafa.client;

import com.ljt.study.huafa.dto.oa.OABaseRequest;
import com.ljt.study.huafa.dto.oa.OABaseResponse;
import com.ljt.study.huafa.enums.OARequestEnum;
import com.ljt.study.huafa.enums.SystemEnum;
import com.ljt.study.huafa.prop.HttpClientProperties;
import com.ljt.study.huafa.prop.OAProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;

/**
 * @author LiJingTang
 * @date 2023-05-24 17:20
 */
public class OAHttpClient extends BaseHttpClient<OARequestEnum, OABaseRequest, OABaseResponse> {

    @Autowired
    private OAProperties oaProperties;


    public <R extends OABaseResponse> R execute(OARequestEnum requestEnum, OABaseRequest req, Class<R> clazz) {
        return super.doExecute(requestEnum, req, clazz);
    }


    @Override
    protected HttpHeaders getHttpHeader() {
        HttpHeaders headers = super.getHttpHeader();
        headers.setBasicAuth(oaProperties.getUsername(), oaProperties.getPassword());
        return headers;
    }

    @Override
    protected String getBaseUrl() {
        return oaProperties.getUrl();
    }

    @Override
    protected SystemEnum getSystem() {
        return SystemEnum.OA;
    }

    @Override
    protected HttpClientProperties getHttpClientProperties() {
        return oaProperties.getHttpclient();
    }

}
