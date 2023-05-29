package com.ljt.study.huafa.dto.customer.response;

import com.ljt.study.huafa.dto.customer.Page;
import lombok.Data;

import java.util.List;

/**
 * @author LiJingTang
 * @date 2023-05-20 14:51
 */
@Data
public class OwnerListResponse extends Page<OwnerListResponse> {

    private List<Owner> lists;


    @Data
    public static class Owner {

        private String id;
        private String mobile;
        private String newMobile;
        private String ownerId;
        private String ownerName;
        private String updateId;
        private String updateTime;
        private String address;
        private String certNumber;
        private String certTypeId;
        private String certTypeName;
        private String createId;
        private String createTime;
        private Integer customerType;
        private Integer gender;
        private List<House> houseRelList;

    }

    @Data
    public static class House {

        private Integer authStatus;
        private String confirmDate;
        private Integer dealStatus;
        private String handoverDate;
        private String houseId;
        private Integer isSecond;
        private Integer ownerRel;
        private String relId;
        private String tenancyTerm;

    }

}
