package com.ljt.study.huafa.dto.customer;

import lombok.Data;

/**
 * @author LiJingTang
 * @date 2023-05-20 14:53
 */
@Data
public class Page<T> extends CustomerBaseResponse<T> {

    private Integer pageNumber;
    private Integer pageSize;
    private Integer totalCount;
    private Integer totalPage;

}
