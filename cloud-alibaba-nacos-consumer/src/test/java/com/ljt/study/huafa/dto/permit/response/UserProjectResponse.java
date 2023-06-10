package com.ljt.study.huafa.dto.permit.response;

import com.ljt.study.huafa.dto.permit.PermitBaseResponse;
import lombok.Data;

import java.util.List;

/**
 * @author LiJingTang
 * @date 2023-05-24 10:26
 */
@Data
public class UserProjectResponse extends PermitBaseResponse<List<UserProjectResponse.UserProject>> {


    @Data
    public static class UserProject {

        /**
         * 	区域ID
         */
        private String areaId;

        /**
         * 区域名称
         */
        private String areaName;

        /**
         * 城市ID
         */
        private String cityId;

        /**
         * 城市名称
         */
        private String cityName;

        /**
         * 股份代码
         */
        private String gufenId;

        /**
         * 股份名称
         */
        private String gufenName;

        /**
         * 组织代码
         */
        private String orgId;

        /**
         * 组织名称
         */
        private String orgName;

        /**
         * 项目代码
         */
        private String projectId;

        /**
         * 项目名称
         */
        private String projectName;

        /**
         * 区域代码（地理位置的概念）
         */
        private String realAreaId;

        /**
         * 区域名称（地理位置的概念）
         */
        private String realAreaName;

        /**
         * 城市代码（地理位置的概念）
         */
        private String realCityId;

        /**
         * realCityName	城市名称（地理位置的概念）
         */
        private String realCityName;


        private List<ProjectPeriod> projectPeriodList;

    }


    @Data
    public static class ProjectPeriod {

        /**
         * 分期代码
         */
        private String periodId;

        /**
         * 分期名称
         */
        private String periodName;

        /**
         * 项目代码
         */
        private String projectId;

        /**
         * 销售组织代码
         */
        private String saleOrgId;

        /**
         * 销售组织名称
         */
        private String saleOrgName;

    }

}
