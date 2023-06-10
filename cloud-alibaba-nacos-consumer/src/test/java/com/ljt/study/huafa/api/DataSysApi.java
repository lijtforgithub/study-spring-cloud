package com.ljt.study.huafa.api;

import com.ljt.study.huafa.dto.data.DataBaseRequest;
import com.ljt.study.huafa.dto.data.response.*;

/**
 * 数仓系统接口
 *
 * @author LiJingTang
 * @date 2023-05-21 11:42
 */
public interface DataSysApi {

    /**
     * 区域公司
     */
    AreaCompanyResponse listAreaCompany(DataBaseRequest request);

    /**
     * 城市公司
     */
    CityCompanyResponse listCityCompany(DataBaseRequest request);

    /**
     * 项目
     */
    ProjectResponse listProject(DataBaseRequest request);

    /**
     * 分期
     */
    StageResponse listStage(DataBaseRequest request);

    /**
     * 楼栋
     */
    BuildingResponse listBuilding(DataBaseRequest request);

    /**
     * 楼栋单元
     */
    BuildingUnitResponse listBuildingUnit(DataBaseRequest request);

    /**
     * 房源基本信息
     */
    HouseResponse listHouse(DataBaseRequest request);

    /**
     * 房源销售信息
     */
    HouseSaleResponse listHouseSale(DataBaseRequest request);

    /**
     * 产品类型/业态
     */
    ProductTypeResponse listProductType(DataBaseRequest request);

}
