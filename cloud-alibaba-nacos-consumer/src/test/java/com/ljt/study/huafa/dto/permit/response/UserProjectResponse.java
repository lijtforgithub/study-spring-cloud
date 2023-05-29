package com.ljt.study.huafa.dto.permit.response;

import com.ljt.study.huafa.dto.permit.PermitBaseResponse;
import lombok.Data;

import java.util.List;

/**
 * @author LiJingTang
 * @date 2023-05-24 10:26
 */
@Data
public class UserProjectResponse extends PermitBaseResponse<List<UserProjectResponse>> {

    private String areaId;
    private String areaName;
    private String cityId;
    private String cityName;
    private String realAreaId;
    private String realAreaName;
    private String realCityId;
    private String realCityName;
    private String projectId;
    private String projectName;
    private String orgId;
    private String gufenId;
    private String gufenName;

}
