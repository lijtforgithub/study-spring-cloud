package com.ljt.study.huafa;

import com.alibaba.fastjson.JSON;
import com.ljt.study.huafa.api.ClinkSysApi;
import com.ljt.study.huafa.dto.clink.ClinkBaseResponse;
import com.ljt.study.huafa.dto.clink.request.*;
import com.ljt.study.huafa.dto.clink.response.ClinkLoginResponse;
import com.ljt.study.huafa.prop.ClinkProperties;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * @author LiJingTang
 * @date 2023-06-07 09:15
 */
@Slf4j
@SpringBootTest
class ClinkSysApiTest {

    @Autowired
    private ClinkSysApi clinkSysApi;
    @Autowired
    private ClinkProperties clinkProperties;

    private ClinkBaseResponse response;

    @AfterEach
    void afterEach() {
        log.info(JSON.toJSONString(response));
    }


    @Test
    void getClientDetail() {
        ClientDetailRequest request = new ClientDetailRequest();
        request.setUsername("xianbinrong");
        response = clinkSysApi.getClientDetail(request);
    }

    @Test
    void getLoginInfo() {
        ClinkLoginRequest request = new ClinkLoginRequest();
        request.setUid("000045711");
        request.setUsername("xianbinrong");
        ClinkLoginResponse response = clinkSysApi.getLoginInfo(request);

        log.info(JSON.toJSONString(response));
    }

    @Test
    void listCallRecord() {
        CallRecordRequest request = new CallRecordRequest();
        request.setCno("88888888");
//        request.setBusinessLineAndSystem(clinkProperties.getBusinessLine());
        request.setStartTime(1652584100L);
        request.setEndTime(1654873700L);
        response = clinkSysApi.listCallRecord(request);
    }

    @Test
    void getCallRecordUrl() {
        CallRecordUrlRequest request = new CallRecordUrlRequest();
        request.setMainUniqueId("medias_1-1653272195.4191");
        String response = clinkSysApi.getCallRecordUrl(request);

        log.info(response);
    }

    @Test
    void onLine() {
        OnLineRequest request = new OnLineRequest();
        request.setUsername("xianbinrong");
        request.setBusinessLine(clinkProperties.getBusinessLine());
        request.setPlatform(clinkProperties.getPlatform());
        request.setBindType(1);
        request.setBindTel("15155965310");
        clinkSysApi.onLine(request);
    }


    public static void main(String[] args) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("xx", ":%?");
        map.add("oo", "%3A");
        System.out.println(UriComponentsBuilder.fromPath("/test").queryParams(map).toUriString());
        System.out.println(UriComponentsBuilder.fromPath("/test").queryParams(map).build().toUriString());
        URI uri = UriComponentsBuilder.fromPath("/test").queryParams(map).build().toUri();
        System.out.println(uri);
    }

}
