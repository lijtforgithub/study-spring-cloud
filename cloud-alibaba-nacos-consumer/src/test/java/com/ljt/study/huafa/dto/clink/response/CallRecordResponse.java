package com.ljt.study.huafa.dto.clink.response;

import lombok.Data;

import java.util.List;

/**
 * @author LiJingTang
 * @date 2023-06-06 11:52
 */
@Data
public class CallRecordResponse extends PageResponse {

    /**
     * 通话记录列表
     */
    private List<CallRecord> cdrs;


    @Data
    public static class CallRecord {

        /**
         * 通话记录唯一标识
         */
        private String uniqueId;

        /**
         * 来电热线号码
         */
        private String hotline;

        /**
         * 客户来电号码，带区号
         */
        private String customerNumber;

        /**
         * 客户来电号码加密串
         */
        private String customerNumberEncrypt;

        /**
         * 客户来电省份
         */
        private String customerProvince;

        /**
         * 客户来电城市
         */
        private String customerCity;

        /**
         * 来电队列号
         */
        private String qno;

        /**
         * 座席号
         */
        private String cno;

        /**
         * 座席名称
         */
        private String clientName;

        /**
         * 座席电话
         */
        private String clientNumber;

        /**
         * 呼入类型
         */
        private String callType;

        /**
         * 开始时间
         */
        private Long startTime;

        /**
         * 结束时间
         */
        private Long endTime;

        /**
         * 接通时间
         */
        private Long bridgeTime;

        /**
         * 接通时长
         */
        private Integer bridgeDuration;

        /**
         * 总时长
         */
        private Integer totalDuration;

        /**
         * IVR名称
         */
        private String ivrName;

        /**
         * 接听状态
         */
        private String status;

        /**
         * 接听状态映射
         */
        private String statusCode;

        /**
         * 挂机原因 1000主通道挂机 1001非主通道挂机 1002被强拆
         */
        private String endReason;

        /**
         * 呼叫结果
         */
        private String sipCause;

        /**
         * 录音文件名
         */
        private String recordFile;

        /**
         * 自定义字段
         */
        private String userField;

        /**
         * 标记
         */
        private Integer mark;

        /**
         * 标签
         */
        private String[] tags;

        /**
         * 满意度按键值
         */
        private Integer investigationScore;
    }

}
