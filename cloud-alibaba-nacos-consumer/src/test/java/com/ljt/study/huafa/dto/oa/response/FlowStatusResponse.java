package com.ljt.study.huafa.dto.oa.response;

import com.ljt.study.huafa.dto.oa.OABaseResponse;
import lombok.Data;

/**
 * @author LiJingTang
 * @date 2023-06-11 16:30
 */
@Data
public class FlowStatusResponse extends OABaseResponse {


    private FlowStatus flow;

    @Data
    public static class FlowStatus {

        /**
         * 具体原因说明
         */
        private String msg;

        /**
         * 流程ID
         */
        private String flowId;

        /**
         * 流程状态
         * -1 未找到该流程
         * 0 flowId不能为空
         * 1 正常结束
         * 2 流程被中止
         * 3 流程正在执行中
         * 4 流程被撤销待发起人重新发起
         * 5 待发
         * 6 退回到发起人修改
         */
        private String flowState;

    }

}
