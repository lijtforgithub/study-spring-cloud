package com.ljt.study.huafa;

import com.alibaba.fastjson.JSON;
import com.ljt.study.huafa.api.IdmSysApi;
import com.ljt.study.huafa.dto.idm.response.UserResponse;
import com.ljt.study.huafa.ldap.idm.enums.SmartTypeEnum;
import com.ljt.study.huafa.ldap.idm.model.IdmPerson;
import com.ljt.study.huafa.ldap.idm.service.IdmPersonService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author LiJingTang
 * @date 2023-05-25 10:12
 */
@Slf4j
@SpringBootTest
class IdmSysApiTest {

    @Autowired
    private IdmSysApi idmSysApi;
    @Autowired
    private IdmPersonService idmPersonService;


    @Test
    void testIdm() {
        UserResponse response = idmSysApi.getOneByUsername("ouyangjunjiang");

        log.info(JSON.toJSONString(response));

        response = idmSysApi.getOneById("000000189");

        log.info(JSON.toJSONString(response));

        for (SmartTypeEnum typeEnum : SmartTypeEnum.values()) {
            long start = System.currentTimeMillis();
            List<IdmPerson> list = idmPersonService.findList(typeEnum, true);

            System.out.println(typeEnum + " - " + list.size() + " - " + (System.currentTimeMillis() - start));
        }
    }

}
