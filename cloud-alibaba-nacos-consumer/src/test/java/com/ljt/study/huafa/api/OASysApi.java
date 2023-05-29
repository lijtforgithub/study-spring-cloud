package com.ljt.study.huafa.api;

import com.ljt.study.huafa.dto.oa.request.FlowStartRequest;
import com.ljt.study.huafa.dto.oa.response.FlowStartResponse;

/**
 * @author LiJingTang
 * @date 2023-05-24 17:25
 */
public interface OASysApi {

    FlowStartResponse startFlow(FlowStartRequest request);

}
