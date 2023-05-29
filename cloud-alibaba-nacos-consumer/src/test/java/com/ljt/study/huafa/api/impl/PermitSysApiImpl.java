package com.ljt.study.huafa.api.impl;

import com.ljt.study.huafa.api.PermitSysApi;
import com.ljt.study.huafa.client.PermitHttpClient;
import com.ljt.study.huafa.dto.permit.request.UserPositionRequest;
import com.ljt.study.huafa.dto.permit.request.UserProjectRequest;
import com.ljt.study.huafa.dto.permit.response.UserPositionResponse;
import com.ljt.study.huafa.dto.permit.response.UserProjectResponse;
import lombok.RequiredArgsConstructor;

import static com.ljt.study.huafa.enums.PermitRequestEnum.V2_GET_USER_POSITION_LIST;
import static com.ljt.study.huafa.enums.PermitRequestEnum.V2_GET_USER_PROJECT_BY_PARAM;

/**
 * @author LiJingTang
 * @date 2023-05-22 16:04
 */
@RequiredArgsConstructor
public class PermitSysApiImpl implements PermitSysApi {

    private final PermitHttpClient client;

    @Override
    public UserPositionResponse getUserPosition(UserPositionRequest request) {
        return client.execute(V2_GET_USER_POSITION_LIST, request, UserPositionResponse.class);
    }

    @Override
    public UserProjectResponse getUserProject(UserProjectRequest request) {
        return client.execute(V2_GET_USER_PROJECT_BY_PARAM, request, UserProjectResponse.class);
    }
}
