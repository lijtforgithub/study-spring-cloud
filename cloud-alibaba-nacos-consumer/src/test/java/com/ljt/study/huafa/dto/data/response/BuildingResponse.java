package com.ljt.study.huafa.dto.data.response;

import com.ljt.study.huafa.dto.data.DataBaseResponse;
import lombok.Data;

/**
 * @author LiJingTang
 * @date 2023-05-21 11:02
 */
@Data
public class BuildingResponse extends DataBaseResponse<BuildingResponse.Building> {


    @Data
    public static class Building extends CommonResponse {

        /**
         * 楼栋ID
         */
        private String buildingId;

        /**
         * 楼栋名称
         */
        private String buildingName;

        /**
         * 项目ID
         */
        private String projectId;

        /**
         * 项目名称
         */
        private String projectName;

        /**
         * 项目推广名
         */
        private String projectCaseName;

        /**
         * 分期ID
         */
        private String stageId;

        /**
         * 分期名称
         */
        private String stageName;

        /**
         * 分期推广名
         */
        private String stageCaseName;

        /**
         * 产品类型ID
         */
        private String productTypeId;

        /**
         * 产品类型名称
         */
        private String productTypeName;

        /**
         * 经营属性
         */
        private String mgtType;

        /**
         * 	竣工日期
         */
        private String completionDate;

        /**
         * 开盘日期
         */
        private String openingDate;

        /**
         * 营销集中交付日期
         */
        private String deliverDate;

        /**
         * ERP系统交付日期
         */
        private String erpDeliverDate;

        /**
         * 计划竣工备案日期
         */
        private String planCompletionDate;

        /**
         * 计划综合验收日期
         */
        private String planAcceptDate;

        /**
         * 综合验收实际完成日期
         */
        private String actualAcceptDate;

        /**
         * 项目计划交付日期
         */
        private String planDeliverDate;

        /**
         * 合同约定交付日期
         */
        private String contractDeliverDate;

        /**
         * 营销集中交付开始日期
         */
        private String deliverStartDate;

    }

}
