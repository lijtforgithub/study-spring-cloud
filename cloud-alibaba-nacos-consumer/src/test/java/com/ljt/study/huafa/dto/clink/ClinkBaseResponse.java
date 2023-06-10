package com.ljt.study.huafa.dto.clink;

import com.ljt.study.huafa.dto.clink.response.ResponseError;
import lombok.Data;

/**
 * @author LiJingTang
 * @date 2023-06-06 11:46
 */
@Data
public class ClinkBaseResponse {

    private String requestId;
    private ResponseError error;

}
