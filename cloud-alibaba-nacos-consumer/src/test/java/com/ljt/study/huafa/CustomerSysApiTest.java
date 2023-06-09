package com.ljt.study.huafa;

import com.alibaba.fastjson.JSON;
import com.ljt.study.huafa.api.CustomerSysApi;
import com.ljt.study.huafa.dto.customer.request.DictRequest;
import com.ljt.study.huafa.dto.customer.request.OwnerIncrRequest;
import com.ljt.study.huafa.dto.customer.response.DictResponse;
import com.ljt.study.huafa.dto.customer.response.OwnerIncrResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.commons.httpclient.ApacheHttpClientFactory;
import org.springframework.cloud.commons.httpclient.OkHttpClientFactory;

import javax.annotation.Resource;

/**
 * @author LiJingTang
 * @date 2023-05-19 18:43
 */
@Slf4j
@SpringBootTest
class CustomerSysApiTest {

    @Autowired(required = false)
    private OkHttpClientFactory okHttpClientFactory;
    @Autowired(required = false)
    private ApacheHttpClientFactory apacheHttpClientFactory;

    @Resource
    private CustomerSysApi customerSysApi;


    @Test
    void testOwner() {
        OwnerIncrRequest request = new OwnerIncrRequest();
        request.setPageNo(10);
        request.setPageSize(10);
//        OwnerIncrResponse response = customerSysApi.getIncrOwner(request);
        OwnerIncrResponse response = customerSysApi.getIncrOwnerRoom(request);

        log.info(JSON.toJSONString(response));
    }

    @Test
    void testDict() {
        DictResponse response = customerSysApi.getDict(new DictRequest());

        log.info(JSON.toJSONString(response));
    }

}
