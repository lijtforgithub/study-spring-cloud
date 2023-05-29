package com.ljt.study.huafa.dto.customer.request;

import com.ljt.study.huafa.dto.customer.CustomerBaseRequest;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author LiJingTang
 * @date 2023-05-20 14:47
 */
@Data
public class OwnerListRequest extends CustomerBaseRequest {

    private String beginTime;
    private String endTime;
    private String updateTime;
    @NotNull(message = "页吗不能为空")
    private Integer pageNo;
    @NotNull(message = "页大小不能为空")
    private Integer pageSize;

}
