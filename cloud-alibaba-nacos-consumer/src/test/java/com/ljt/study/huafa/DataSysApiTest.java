package com.ljt.study.huafa;

import com.alibaba.fastjson.JSON;
import com.ljt.study.huafa.api.DataSysApi;
import com.ljt.study.huafa.dto.data.DataBaseRequest;
import com.ljt.study.huafa.dto.data.DataBaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author LiJingTang
 * @date 2023-05-21 11:48
 */
@Slf4j
@SpringBootTest
class DataSysApiTest {

    @Autowired
    private DataSysApi dataSysApi;

    private DataBaseRequest request;
    private DataBaseResponse<?> response;

    @BeforeEach
    void beforeEach() {
        request = new DataBaseRequest();
        request.setPageNo(1);
        request.setPageSize(10);
    }

    @AfterEach
    void afterEach() {
        log.info(JSON.toJSONString(response));
    }


    @Test
    void listAreaCompany() {
        response = dataSysApi.listAreaCompany(request);
    }

    @Test
    void listCityCompany() {
        response = dataSysApi.listCityCompany(request);
    }

    @Test
    void listProject() {
        response = dataSysApi.listProject(request);
    }

    @Test
    void listStage() {
        response = dataSysApi.listStage(request);
    }

    @Test
    void listBuilding() {
        response = dataSysApi.listBuilding(request);
    }

    @Test
    void listBuildingUnit() {
        response = dataSysApi.listBuildingUnit(request);
    }

    @Test
    void listHouse() {
        response = dataSysApi.listHouse(request);
    }

    @Test
    void listHouseSale() {
        response = dataSysApi.listHouseSale(request);
    }

    @Test
    void listProductType() {
        response = dataSysApi.listProductType(request);
    }

}
