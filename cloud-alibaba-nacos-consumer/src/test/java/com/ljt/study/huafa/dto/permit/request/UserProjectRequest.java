package com.ljt.study.huafa.dto.permit.request;

import com.ljt.study.huafa.dto.permit.PermitBaseRequest;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author LiJingTang
 * @date 2023-05-24 10:24
 */
@Data
public class UserProjectRequest extends PermitBaseRequest {

    /**
     * 用户ID
     */
    @NotBlank(message = "用户ID不能为空")
    private String userId;

    /**
     * 区域代码
     */
    private String areaId;

    /**
     * 区域名称
     */
    private String areaName;

    /**
     * 城市代码
     */
    private String cityId;

    /**
     * 	城市名称
     */
    private String cityName;

    /**
     * 客户端编码
     */
    private String clientCode;

    /**
     * 股份代码
     */
    private String gufenId;

    /**
     * 股份名称
     */
    private String gufenName;

    /**
     * 组织代码
     */
    private String orgId;

    /**
     * 组织名称
     */
    private String orgName;

    /**
     * 岗位编码
     */
    private String positionId;

    /**
     * 项目代码
     */
    private String projectId;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 区域代码（地理位置的概念）
     */
    private String realAreaId;

    /**
     * 区域名称（地理位置的概念）
     */
    private String realAreaName;

    /**
     * 城市代码（地理位置的概念）
     */
    private String realCityId;

    /**
     * realCityName	城市名称（地理位置的概念）
     */
    private String realCityName;

}
