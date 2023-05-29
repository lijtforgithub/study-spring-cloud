package com.ljt.study.huafa.dto.permit.response;

import com.ljt.study.huafa.dto.permit.PermitBaseResponse;
import lombok.Data;

import java.util.List;

/**
 * @author LiJingTang
 * @date 2023-05-22 15:54
 */
@Data
public class UserPositionResponse extends PermitBaseResponse<List<UserPositionResponse>> {

//    @ApiModelProperty("区域编码")
    private String areaCode;
//    @ApiModelProperty("区域名称")
    private String areaName;
//    @ApiModelProperty("城市编码")
    private String cityCode;
//    @ApiModelProperty("城市名称")
    private String cityName;
//    @ApiModelProperty("创建时间")
    private String createTime;
//    @ApiModelProperty("有效从")
    private String dateFrom;
//    @ApiModelProperty("有效至")
    private String dateTo;
//    @ApiModelProperty("邮箱")
    private String email;
//    @ApiModelProperty("员工编号")
    private String employeeNum;
//    @ApiModelProperty("营销系统用户")
    private String gfBase;
//    @ApiModelProperty("是否锁定0=正常,1=锁定")
    private String locked;
//    @ApiModelProperty("是否启用移动端,0=启用,1=不启用")
    private String mobile;
//    @ApiModelProperty("营销系统原来的用户id")
    private String oldUserId;
//    @ApiModelProperty("离职日期")
    private String outdutyDate;
//    @ApiModelProperty("岗位id")
    private String positionId;
//    @ApiModelProperty("岗位名称")
    private String positionName;
//    @ApiModelProperty("账号所属组织名称")
    private String smartOrgNameFullPath;
//    @ApiModelProperty("账号所属组织名称")
    private String smartOrgNoFullPath;
//    @ApiModelProperty("电话号码")
    private String telephone;
//    @ApiModelProperty("更新时间")
    private String updateTime;
//    @ApiModelProperty("用户账号")
    private String userAlias;
//    @ApiModelProperty("用户归属")
    private String userBelong;
//    @ApiModelProperty("用户code")
    private String userId;
//    @ApiModelProperty("用户客服系统id")
    private Long id;
//    @ApiModelProperty("用户名称")
    private String userName;
//    @ApiModelProperty("是否启动,0=启用,1=不启用")
    private String valid;

}
