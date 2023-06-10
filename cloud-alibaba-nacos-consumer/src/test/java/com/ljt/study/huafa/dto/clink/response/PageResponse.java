package com.ljt.study.huafa.dto.clink.response;

import com.ljt.study.huafa.dto.clink.ClinkBaseResponse;
import lombok.Data;

/**
 * @author LiJingTang
 * @date 2023-06-10 13:08
 */
@Data
public class PageResponse extends ClinkBaseResponse {

    /**
     * 当前页码
     */
    private Integer pageNumber;
    /**
     * 一页展示条数
     */
    private Integer pageSize;
    /**
     * 总条数
     */
    private Integer totalCount;

}
