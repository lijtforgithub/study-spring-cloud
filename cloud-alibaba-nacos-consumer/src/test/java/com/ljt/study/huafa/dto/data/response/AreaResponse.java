package com.ljt.study.huafa.dto.data.response;

import com.ljt.study.huafa.dto.data.DataBaseResponse;
import lombok.Data;

/**
 * @author LiJingTang
 * @date 2023-05-21 11:02
 */
@Data
public class AreaResponse extends DataBaseResponse<AreaResponse> {

    private String areaId;
    private String areaName;
    private Integer delFlag;
    private String createDate;
    private String createBy;
    private String updateDate;
    private String updateBy;

}
