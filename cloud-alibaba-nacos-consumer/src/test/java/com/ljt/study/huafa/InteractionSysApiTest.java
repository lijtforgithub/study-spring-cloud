package com.ljt.study.huafa;

import com.alibaba.fastjson.JSON;
import com.ljt.study.huafa.api.InteractionSysApi;
import com.ljt.study.huafa.dto.interaction.request.SmsSingleRequest;
import com.ljt.study.huafa.dto.interaction.response.SmsSingleResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
    void testSmsSendSingle() {
        SmsSingleRequest request = new SmsSingleRequest();
        request.setMobile("15155965310");
        request.setMessage("{\"roomNum\":\"珠海华发蔚蓝堡（98-108栋）-一期-108栋-1单元-102\",\"url\":\"weixin://dl/business/?t=RBzqXMEPexn\"}");
        request.setCreateId("test");
        request.setTemplateNo("KF_081");
        SmsSingleResponse response = interactionSysApi.smsSendSingle(request);

        log.info(JSON.toJSONString(response));
    }

}
