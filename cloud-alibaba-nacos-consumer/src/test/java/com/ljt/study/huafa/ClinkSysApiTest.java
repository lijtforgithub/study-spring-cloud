package com.ljt.study.huafa;

import com.alibaba.fastjson.JSON;
import com.ljt.study.huafa.api.ClinkSysApi;
import com.ljt.study.huafa.dto.clink.request.AllCdrRequest;
import com.ljt.study.huafa.dto.clink.response.AllCdrResponse;
import lombok.extern.slf4j.Slf4j;
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


    @Test
    void listAllCdr() {
        AllCdrRequest request = new AllCdrRequest();
        request.setCno("88888888");
//        request.setType(1);
        request.setRequestUniqueId("1234");
        request.setBusinessLineAndSystem("test");
        AllCdrResponse response = clinkSysApi.listAllCdr(request);

        log.info(JSON.toJSONString(response));
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
