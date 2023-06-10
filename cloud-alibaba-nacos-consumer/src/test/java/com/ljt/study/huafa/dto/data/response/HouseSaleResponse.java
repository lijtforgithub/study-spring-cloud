package com.ljt.study.huafa.dto.data.response;

import com.ljt.study.huafa.dto.data.DataBaseResponse;
import lombok.Data;

/**
 * @author LiJingTang
 * @date 2023-05-21 11:02
 */
@Data
public class HouseSaleResponse extends DataBaseResponse<HouseSaleResponse.HouseSale> {


    @Data
    public static class HouseSale extends CommonResponse {

        /**
         * 房源ID（营销系统）
         */
        private String houseId;

        /**
         * 签约时间
         */
        private String contractTime;

        /**
         * 销售房间名
         */
        private String salesName;

        /**
         * 房间状态编码
         */
        private String houseStatusNo;

        /**
         * 入伙登记日期
         */
        private String partnershipRegistrationDate;

        /**
         * 收楼通知书起始日期
         */
        private String takeNoticeStartDate;

        /**
         * 交房状态（1未交房，2已交房）
         */
        private Integer deliverState;

        /**
         * 实际交房时间
         */
        private String deliverTime;

        /**
         * 合同约定交楼日期
         */
        private String contractDeliveryDate;

        /**
         * 视同交楼日期
         */
        private String deemedDeliveryDate;

        /**
         * 销控状态 1-销控 2-待售 3-认购 4-签约 5-已交付
         */
        private Integer houseControlStatus;

        /**
         * 销控原因
         */
        private String houseControlReason;

        /**
         * 集中交付日期
         */
        private String deliverDate;

        /**
         * 竣工日期
         */
        private String completionDate;

    }

}
