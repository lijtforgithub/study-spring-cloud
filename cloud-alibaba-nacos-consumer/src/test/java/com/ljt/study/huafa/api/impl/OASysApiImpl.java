package com.ljt.study.huafa.api.impl;

import com.ljt.study.huafa.api.OASysApi;
import com.ljt.study.huafa.client.OAHttpClient;
import com.ljt.study.huafa.dto.oa.request.StartFlowRequest;
import com.ljt.study.huafa.dto.oa.response.StartFlowResponse;
import com.ljt.study.huafa.enums.OARequestEnum;
import lombok.RequiredArgsConstructor;

/**
 * @author LiJingTang
 * @date 2023-05-24 17:26
 */
@RequiredArgsConstructor
public class OASysApiImpl implements OASysApi {

    private final OAHttpClient client;

    @Override
    public StartFlowResponse startFlow(StartFlowRequest request) {
        return client.execute(OARequestEnum.START_FLOW, request, StartFlowResponse.class);
    }

}
