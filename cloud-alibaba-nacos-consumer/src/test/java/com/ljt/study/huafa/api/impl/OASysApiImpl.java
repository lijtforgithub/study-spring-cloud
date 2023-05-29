package com.ljt.study.huafa.api.impl;

import com.ljt.study.huafa.api.OASysApi;
import com.ljt.study.huafa.client.OAHttpClient;
import com.ljt.study.huafa.dto.oa.request.FlowStartRequest;
import com.ljt.study.huafa.dto.oa.response.FlowStartResponse;
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
    public FlowStartResponse startFlow(FlowStartRequest request) {
        return client.execute(OARequestEnum.FLOW_START, request, FlowStartResponse.class);
    }

}
