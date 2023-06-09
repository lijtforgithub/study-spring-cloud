package com.ljt.study.huafa;

import com.alibaba.fastjson.JSON;
import com.ljt.study.huafa.api.DataSysApi;
import com.ljt.study.huafa.dto.data.request.AreaRequest;
import com.ljt.study.huafa.dto.data.response.AreaResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

/**
 * @author LiJingTang
 * @date 2023-05-21 11:48
 */
@Slf4j
@SpringBootTest
class DataSysApiTest {

    @Autowired
    private DataSysApi dataSysApi;


    @Test
    void listArea() {
        AreaRequest request = new AreaRequest();
        request.setUpdateDate(LocalDate.now().minusDays(1));
        request.setPageNo(1);
        request.setPageSize(3);
        AreaResponse response = dataSysApi.listArea(request);

        log.info(JSON.toJSONString(response));
    }

}
