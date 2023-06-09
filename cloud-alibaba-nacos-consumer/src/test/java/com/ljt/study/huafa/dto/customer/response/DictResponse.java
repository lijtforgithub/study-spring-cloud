package com.ljt.study.huafa.dto.customer.response;

import com.ljt.study.huafa.dto.customer.CustomerBaseResponse;
import lombok.Data;

import java.util.List;

/**
 * @author LiJingTang
 * @date 2023-06-05 16:04
 */
@Data
public class DictResponse extends CustomerBaseResponse<List<DictResponse>> {

    private String itemName;
    private String itemValue;
    private Integer status;

}
