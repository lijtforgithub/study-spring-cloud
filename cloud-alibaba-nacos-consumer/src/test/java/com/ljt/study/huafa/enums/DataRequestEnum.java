package com.ljt.study.huafa.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpMethod;

/**
 * @author LiJingTang
 * @date 2023-05-21 10:22
 */
@Getter
@AllArgsConstructor
public enum DataRequestEnum implements RequestEnum {

    LIST_AREA_COMPANY("/api/gfmd/getAreaCompanyList", "股份-区域公司主数据", HttpMethod.POST),
    LIST_CITY_COMPANY("/api/gfmd/getCityCompanyList", "股份-城市公司主数据", HttpMethod.POST),
    LIST_PROJECT("/api/gfmd/getProjectList", "股份-项目主数据", HttpMethod.POST),
    LIST_STAGE("/api/gfmd/getStageList", "股份-分期主数据", HttpMethod.POST),
    LIST_BUILDING("/api/gfmd/getBuildingList", "股份-楼栋信息主数据", HttpMethod.POST),
    LIST_BUILDING_UNIT("/api/gfmd/getUnitList", "股份-单元信息主数据", HttpMethod.POST),
    LIST_HOUSE("/api/gfmd/getHouseInfoList", "股份-房源基础信息", HttpMethod.POST),
    LIST_HOUSE_SALE("/api/gfmd/getHouseSalesList", "股份-房源销售信息", HttpMethod.POST),
    LIST_PRODUCT_TYPE("/api/gfmd/getProductTypeList", "股份-产品类型", HttpMethod.POST);

    private final String url;
    private final String desc;
    private final HttpMethod method;

}
