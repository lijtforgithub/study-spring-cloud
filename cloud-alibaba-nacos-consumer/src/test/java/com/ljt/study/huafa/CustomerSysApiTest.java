package com.ljt.study.huafa;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.ljt.study.huafa.api.CustomerSysApi;
import com.ljt.study.huafa.dto.customer.request.OwnerInfoRequest;
import com.ljt.study.huafa.dto.customer.request.OwnerListRequest;
import com.ljt.study.huafa.dto.customer.response.OwnerInfoResponse;
import com.ljt.study.huafa.dto.customer.response.OwnerListResponse;
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
    void testOwnerList() {
        OwnerListRequest request = new OwnerListRequest();
        request.setPageNo(1);
        request.setPageSize(1000);
        OwnerListResponse response = customerSysApi.getOwnerList(request);

        response.getLists().removeIf(next -> StrUtil.isBlank(next.getNewMobile()));

        log.info(JSON.toJSONString(response));
    }

    @Test
    void testOwnerInfo() {
        OwnerInfoRequest request = new OwnerInfoRequest();
        OwnerInfoResponse response = customerSysApi.getOwnerInfo(request);

        response.getData().removeIf(next -> StrUtil.isBlank(next.getNewMobile()) || next.getNewMobile().length() == 11);

        log.info(JSON.toJSONString(response));
    }

}
