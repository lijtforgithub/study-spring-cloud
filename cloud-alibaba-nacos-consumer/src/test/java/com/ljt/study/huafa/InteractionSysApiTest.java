package com.ljt.study.huafa;

import com.ljt.study.huafa.api.InteractionSysApi;
import com.ljt.study.huafa.dto.interaction.request.SmsSingleRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

/**
 * @author LiJingTang
 * @date 2023-05-22 09:23
 */
@Slf4j
@SpringBootTest
class InteractionSysApiTest {

    @Autowired
    private InteractionSysApi interactionSysApi;


    @Test
    void sendSingleSms() {
        String uuid = UUID.randomUUID().toString();
        log.info("短信流水号={}", uuid);
        SmsSingleRequest request = new SmsSingleRequest();
        request.setAppSerialNo(uuid);
        request.setMobile("15155965310");
        request.setCreateId("test");
        request.setTemplateNo("KF_081");
        request.setMessage("{\"roomNum\":\"珠海华发蔚蓝堡（98-108栋）-一期-108栋-1单元-102\",\"url\":\"weixin://dl/business/?t=RBzqXMEPexn\"}");
//        request.setMessage("xxoo");
        String response = interactionSysApi.sendSingleSms(request);

        log.info(response);
    }

    @Test
    void getSmsStatus() {
        // 24441e88-7b1f-4387-ae4c-b73a7eb122e5
        String response = interactionSysApi.getSmsStatus("3374bfee-4a27-4db7-998a-150833034a5d");

        log.info(response);
    }

}
