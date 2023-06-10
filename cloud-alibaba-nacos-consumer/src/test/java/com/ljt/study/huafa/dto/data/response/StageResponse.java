package com.ljt.study.huafa.dto.data.response;

import com.ljt.study.huafa.dto.data.DataBaseResponse;
import lombok.Data;

/**
 * @author LiJingTang
 * @date 2023-05-21 11:02
 */
@Data
public class StageResponse extends DataBaseResponse<StageResponse.Stage> {


    @Data
    public static class Stage extends CommonResponse {

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
         * 销售组织ID
         */
        private String saleOrgId;

        /**
         * 销售组织名称
         */
        private String saleOrgName;

        /**
         * 是否操盘
         */
        private String cpFlag;

        /**
         * 是否投资操盘
         */
        private String omtz;

        /**
         * 是否设计操盘
         */
        private String omsj;

        /**
         * 是否工程操盘
         */
        private String omgc;

        /**
         * 是否成本操盘
         */
        private String omcb;

        /**
         * 是否招采操盘
         */
        private String omzc;

        /**
         * 是否营销操盘
         */
        private String omyx;

        /**
         * 是否运营操盘
         */
        private String omyy;

        /**
         * 是否报建操盘
         */
        private String ombj;

        /**
         * 是否综合操盘
         */
        private String omzh;

        /**
         * 是否客服操盘
         */
        private String omkf;

        /**
         * 是否物业操盘
         */
        private String omwy;

        /**
         * 合作方名称
         */
        private String partnerName;

    }

}
