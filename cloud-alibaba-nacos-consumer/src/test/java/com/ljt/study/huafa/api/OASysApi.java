package com.ljt.study.huafa.api;

import com.ljt.study.huafa.dto.oa.request.StartFlowRequest;
import com.ljt.study.huafa.dto.oa.response.StartFlowResponse;

/**
 * @author LiJingTang
 * @date 2023-05-24 17:25
 */
public interface OASysApi {

    StartFlowResponse startFlow(StartFlowRequest request);

}
