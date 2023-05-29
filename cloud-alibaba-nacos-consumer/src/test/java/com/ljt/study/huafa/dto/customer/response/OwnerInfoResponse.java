package com.ljt.study.huafa.dto.customer.response;

import com.ljt.study.huafa.dto.customer.CustomerBaseResponse;
import lombok.Data;

import java.util.List;

/**
 * @author LiJingTang
 * @date 2023-05-29 08:53
 */
@Data
public class OwnerInfoResponse extends CustomerBaseResponse<List<OwnerInfoResponse>> {

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

}
