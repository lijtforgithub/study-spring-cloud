package com.ljt.study.huafa.dto.oa.response;

import com.ljt.study.huafa.dto.oa.OABaseResponse;
import lombok.Data;

/**
 * @author LiJingTang
 * @date 2023-05-23 19:08
 */
@Data
public class FlowStartResponse extends OABaseResponse {

    private String state;
    private String isSuccess;
    private String msg;
    private String flowId;

}
