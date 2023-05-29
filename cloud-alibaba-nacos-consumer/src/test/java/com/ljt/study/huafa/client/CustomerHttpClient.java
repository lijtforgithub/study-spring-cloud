package com.ljt.study.huafa.client;

import com.ljt.study.huafa.dto.customer.CustomerBaseRequest;
import com.ljt.study.huafa.dto.customer.CustomerBaseResponse;
import com.ljt.study.huafa.enums.CustomerRequestEnum;
import com.ljt.study.huafa.enums.SystemEnum;
import com.ljt.study.huafa.prop.CustomerProperties;
import com.ljt.study.huafa.prop.HttpClientProperties;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author LiJingTang
 * @date 2023-05-19 21:05
 */
public class CustomerHttpClient extends GatewayHttpClient<CustomerRequestEnum, CustomerBaseRequest, CustomerBaseResponse<?>> {

    @Autowired
    private CustomerProperties customerProperties;


    public <R extends CustomerBaseResponse<?>> R execute(CustomerRequestEnum requestEnum, CustomerBaseRequest req, Class<R> clazz) {
        return super.doExecute(requestEnum, req, clazz);
    }


    @Override
    protected SystemEnum getSystem() {
        return SystemEnum.CUSTOMER;
    }

    @Override
    protected String getBaseUrl() {
        return customerProperties.getUrl();
    }

    @Override
    protected HttpClientProperties getHttpClientProperties() {
        return customerProperties.getHttpclient();
    }

}
