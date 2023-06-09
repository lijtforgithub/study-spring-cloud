package com.ljt.study.huafa.dto.customer.response;

import lombok.Data;

import java.util.List;

/**
 * @author LiJingTang
 * @date 2023-05-20 14:51
 */
@Data
public class OwnerIncrResponse extends PageResponse<OwnerIncrResponse> {

    private List<Owner> lists;


    @Data
    public static class Owner {

        /**
         * 地址
         */
        private String address;
        /**
         * 认证状态
         */
        private Integer authStatus;
        /**
         * 证件号码
         */
        private String certNumber;
        /**
         * 证件类型id
         */
        private String certTypeId;
        /**
         * 证件类型名称
         */
        private String certTypeName;
        /**
         * 交房单打印确认日期
         */
        private Long confirmDate;
        /**
         * 客户类型
         */
        private Integer customerType;
        /**
         * 删除标记 1 已删除，0未删除
         */
        private Integer deleted;
        /**
         * 房客关系id
         */
        private String ownerHouseRelId;
        /**
         * 交易状态
         */
        private Integer dealStatus;
        /**
         * 性别（0男1女2未知）
         */
        private Integer gender;
        /**
         * 	小业主交付日期
         */
        private Long handoverDate;
        /**
         * 	房间id
         */
        private String houseId;
        /**
         * 是否二手业主
         */
        private Long isSecond;
        /**
         * 手机号
         */
        private String mobile;
        /**
         * 客户id
         */
        private String ownerId;
        /**
         * 姓名
         */
        private String ownerName;
        /**
         * 与业主关系
         */
        private Integer ownerRel;
        /**
         * 租期
         */
        private Long tenancyTerm;
    }

}
