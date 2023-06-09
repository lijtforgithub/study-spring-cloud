package com.ljt.study.huafa;

import com.alibaba.fastjson.JSON;
import com.ljt.study.huafa.api.PermitSysApi;
import com.ljt.study.huafa.dto.permit.request.UserPositionRequest;
import com.ljt.study.huafa.dto.permit.request.UserProjectRequest;
import com.ljt.study.huafa.dto.permit.response.UserPositionResponse;
import com.ljt.study.huafa.dto.permit.response.UserProjectResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author LiJingTang
 * @date 2023-05-22 16:07
 */
@Slf4j
@SpringBootTest
class PermitSysApiTest {

    @Autowired
    private PermitSysApi permitSysApi;


    @Test
    void listUserPosition() {
        UserPositionRequest request = new UserPositionRequest();
        request.setUserId("000011395");
        UserPositionResponse response = permitSysApi.listUserPosition(request);

        log.info(JSON.toJSONString(response));
    }

    @Test
    void listUserProject() {
        UserProjectRequest request = new UserProjectRequest();
        request.setUserId("000011395");
        UserProjectResponse response = permitSysApi.listUserProject(request);

        log.info(JSON.toJSONString(response));
    }

}
