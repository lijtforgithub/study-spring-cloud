package com.ljt.study.huafa;

import com.ljt.study.huafa.client.MpmHttpClient;
import com.ljt.study.huafa.dto.mpm.MpmBaseRequest;
import com.ljt.study.huafa.dto.mpm.MpmBaseResponse;
import com.ljt.study.huafa.enums.MpmRequestEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author LiJingTang
 * @date 2023-06-15 16:31
 */
@SpringBootTest
class MpmSysTest {

    @Autowired
    private MpmHttpClient client;


    @Test
    void test() {
        MpmBaseRequest request = new MpmBaseRequest();
        client.execute(MpmRequestEnum.PUSH_TEXT, request, MpmBaseResponse.class);
    }

}
