package com.ljt.study.huafa.dto.data.response;

import com.alibaba.fastjson.annotation.JSONField;
import com.ljt.study.huafa.dto.data.DataBaseResponse;
import lombok.Data;

/**
 * @author LiJingTang
 * @date 2023-05-21 11:02
 */
@Data
public class ProjectResponse extends DataBaseResponse<ProjectResponse.Project> {


    @Data
    public static class Project extends CommonResponse {

        /**
         * 项目ID
         */
        private String projectId;

        /**
         * 项目名称
         */
        private String projectName;

        /**
         * 别名/曾用名
         */
        private String alias;

        /**
         * 项目地址
         */
        private String address;

        /**
         * 销售案名
         */
        private String projectCaseName;

        /**
         * 项目描述
         */
        private String descr;

        /**
         * 项目经度
         */
        private Double latitude;

        /**
         * 项目纬度
         */
        private Double longitude;

        /**
         * 销售备案名
         */
        private String recordName;

        /**
         * 组织结构ID
         */
        private String organizationId;

        /**
         * 组织名
         */
        private String organizationName;

        /**
         * 	区域公司ID
         */
        private String areaCompanyId;

        /**
         * 	区域公司名称
         */
        private String areaCompanyName;

        /**
         * 城市公司ID
         */
        private String cityCompanyId;

        /**
         * 城市公司名称
         */
        private String cityCompanyName;

        /**
         * OA组织编码
         */
        private String oaOrgName;

        /**
         * 地理城市ID
         */
        @JSONField(name = "city_id")
        private String cityId;

        /**
         * 地理城市名称
         */
        @JSONField(name = "city_name")
        private String cityName;

        /**
         * 地理区域ID
         */
        @JSONField(name = "area_id")
        private String areaId;

        /**
         * 地理区域名称
         */
        @JSONField(name = "area_name")
        private String areaName;

        /**
         * 建筑面积-最低
         */
        private Double fMinArea;

        /**
         * 建筑面积-最高
         */
        private Double fMaxArea;

        /**
         * 开发商
         */
        private String fKdevelopersBrandName;

        /**
         * 车位-地上
         */
        private Long fCarportCount;

        /**
         * 车位-地下
         */
        private Long fCarportDownCount;

        /**
         * 占地面积
         */
        private Double fCoversArea;

        /**
         * 容积率
         */
        private Double fPlotRatio;

        /**
         * 绿化率
         */
        private Double fGreeningRate;

        /**
         * erp项目ID
         */
        private String erpId;

    }

}
