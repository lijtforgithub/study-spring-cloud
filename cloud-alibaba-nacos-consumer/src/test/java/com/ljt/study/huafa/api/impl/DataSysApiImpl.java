package com.ljt.study.huafa.api.impl;

import com.ljt.study.huafa.api.DataSysApi;
import com.ljt.study.huafa.client.DataHttpClient;
import com.ljt.study.huafa.dto.data.DataBaseRequest;
import com.ljt.study.huafa.dto.data.response.*;
import lombok.RequiredArgsConstructor;

import static com.ljt.study.huafa.enums.DataRequestEnum.*;

/**
 * @author LiJingTang
 * @date 2023-05-21 11:44
 */
@RequiredArgsConstructor
public class DataSysApiImpl implements DataSysApi {

    private final DataHttpClient client;

    @Override
    public AreaCompanyResponse listAreaCompany(DataBaseRequest request) {
        return client.execute(LIST_AREA_COMPANY, request, AreaCompanyResponse.class);
    }

    @Override
    public CityCompanyResponse listCityCompany(DataBaseRequest request) {
        return client.execute(LIST_CITY_COMPANY, request, CityCompanyResponse.class);
    }

    @Override
    public ProjectResponse listProject(DataBaseRequest request) {
        return client.execute(LIST_PROJECT, request, ProjectResponse.class);
    }

    @Override
    public StageResponse listStage(DataBaseRequest request) {
        return client.execute(LIST_STAGE, request, StageResponse.class);
    }

    @Override
    public BuildingResponse listBuilding(DataBaseRequest request) {
        return client.execute(LIST_BUILDING, request, BuildingResponse.class);
    }

    @Override
    public BuildingUnitResponse listBuildingUnit(DataBaseRequest request) {
        return client.execute(LIST_BUILDING_UNIT, request, BuildingUnitResponse.class);
    }

    @Override
    public HouseResponse listHouse(DataBaseRequest request) {
        return client.execute(LIST_HOUSE, request, HouseResponse.class);
    }

    @Override
    public HouseSaleResponse listHouseSale(DataBaseRequest request) {
        return client.execute(LIST_HOUSE_SALE, request, HouseSaleResponse.class);
    }

    @Override
    public ProductTypeResponse listProductType(DataBaseRequest request) {
        return client.execute(LIST_PRODUCT_TYPE, request, ProductTypeResponse.class);
    }

}
