package com.ljt.study.huafa.dto.data.response;

import com.ljt.study.huafa.dto.data.DataBaseResponse;
import lombok.Data;

/**
 * @author LiJingTang
 * @date 2023-05-21 11:02
 */
@Data
public class BuildingUnitResponse extends DataBaseResponse<BuildingUnitResponse.BuildingUnit> {


    @Data
    public static class BuildingUnit extends CommonResponse {

        /**
         * 单元ID
         */
        private String unitId;

        /**
         * 单元名称
         */
        private String unitName;

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
         * 楼栋ID
         */
        private String buildingId;

        /**
         * 楼栋名称
         */
        private String buildingName;

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

    }

}
