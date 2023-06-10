package com.ljt.study.huafa;

import com.alibaba.fastjson.JSON;
import com.ljt.study.huafa.api.PermitSysApi;
import com.ljt.study.huafa.dto.permit.PermitBaseResponse;
import com.ljt.study.huafa.dto.permit.request.UserMenuRequest;
import com.ljt.study.huafa.dto.permit.request.UserPermissionRequest;
import com.ljt.study.huafa.dto.permit.request.UserPositionRequest;
import com.ljt.study.huafa.dto.permit.request.UserProjectRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
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

    private PermitBaseResponse<?> response;

    @AfterEach
    void afterEach() {
        log.info(JSON.toJSONString(response));
    }


    @Test
    void listUserPosition() {
        UserPositionRequest request = new UserPositionRequest();
        request.setClientCode("CUSTOMER_SERVICE");
        request.setUserId("000011395");
        response = permitSysApi.listUserPosition(request);
    }

    @Test
    void listUserProject() {
        UserProjectRequest request = new UserProjectRequest();
        request.setUserId("000011395");
        response = permitSysApi.listUserProject(request);
    }

    @Test
    void listUserMenu() {
        UserMenuRequest request = new UserMenuRequest();
        request.setUserId("000011395");
        request.setClientCode("CUSTOMER_SERVICE");
        request.setPositionId("kf_gw_0006");
        response = permitSysApi.listUserMenu(request);
    }

    @Test
    void listUserPermission() {
        UserPermissionRequest request = new UserPermissionRequest();
        request.setUserId("000011395");
        request.setClientCode("CUSTOMER_SERVICE");
        response = permitSysApi.listUserPermission(request);
    }

}
