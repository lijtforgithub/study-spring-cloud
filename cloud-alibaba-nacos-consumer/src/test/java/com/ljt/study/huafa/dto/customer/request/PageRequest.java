package com.ljt.study.huafa.dto.customer.request;

import com.ljt.study.huafa.dto.customer.CustomerBaseRequest;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author LiJingTang
 * @date 2023-05-31 13:49
 */
@Data
public class PageRequest extends CustomerBaseRequest {

    /**
     * 开始时间（不为空就取创建时间大于等于开始时间）
     */
    private String beginTime;
    /**
     * 结束时间（不为空就取创建时间小于等于结束时间）
     */
    private String endTime;
    /**
     * 更新时间（不为空就取更新时间大于等于该时间的）
     */
    private String updateTime;
    @NotNull(message = "页吗不能为空")
    private Integer pageNo;
    @NotNull(message = "页大小不能为空")
    private Integer pageSize;

}
