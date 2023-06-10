package com.ljt.study.huafa.dto.data.response;

import com.ljt.study.huafa.dto.data.DataBaseResponse;
import lombok.Data;

/**
 * @author LiJingTang
 * @date 2023-05-21 11:02
 */
@Data
public class HouseResponse extends DataBaseResponse<HouseResponse.House> {


    @Data
    public static class House extends CommonResponse {

        /**
         * 房源ID（营销系统）
         */
        private String houseId;

        /**
         * 房源号码
         */
        private String houseNo;

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
         * 楼栋ID
         */
        private String buildingId;

        /**
         * 楼栋名称
         */
        private String buildingName;

        /**
         * 单元ID
         */
        private String unitId;

        /**
         * 单元名称
         */
        private String unitName;

        /**
         * 产品类型ID
         */
        private String productTypeId;

        /**
         * 产品类型名称
         */
        private String productTypeName;

        /**
         * 装修标准
         */
        private String decorationStandard;

        /**
         * 户型ID
         */
        private String houseTypeId;

        /**
         * 户型名称
         */
        private String houseTypeName;

        /**
         * 楼层号
         */
        private Long floorNo;

        /**
         * 楼层名称
         */
        private String floorName;

        /**
         * 实际房间序号（单元内）
         */
        private Long roomSeq;

        /**
         * 装修标准
         */
        private String doorNo;

        /**
         * 实测建筑面积
         */
        private Double actualFloorArea;

        /**
         * 预测建筑面积
         */
        private Double forecastFloorArea;

        /**
         * 实测套内面积
         */
        private Double actualInnerArea;

        /**
         * 建筑房间名称
         */
        private String buildingRoomName;

        /**
         * 预测套内面积
         */
        private Double forecastInnerArea;

        /**
         * 经营属性
         */
        private String mgtType;

    }

}
