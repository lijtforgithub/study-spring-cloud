package com.ljt.study.huafa.api.impl;

import com.ljt.study.huafa.api.PermitSysApi;
import com.ljt.study.huafa.client.PermitHttpClient;
import com.ljt.study.huafa.dto.permit.request.UserMenuRequest;
import com.ljt.study.huafa.dto.permit.request.UserPermissionRequest;
import com.ljt.study.huafa.dto.permit.request.UserPositionRequest;
import com.ljt.study.huafa.dto.permit.request.UserProjectRequest;
import com.ljt.study.huafa.dto.permit.response.UserMenuResponse;
import com.ljt.study.huafa.dto.permit.response.UserPermissionResponse;
import com.ljt.study.huafa.dto.permit.response.UserPositionResponse;
import com.ljt.study.huafa.dto.permit.response.UserProjectResponse;
import lombok.RequiredArgsConstructor;

import static com.ljt.study.huafa.enums.PermitRequestEnum.*;

/**
 * @author LiJingTang
 * @date 2023-05-22 16:04
 */
@RequiredArgsConstructor
public class PermitSysApiImpl implements PermitSysApi {

    private final PermitHttpClient client;

    @Override
    public UserPositionResponse listUserPosition(UserPositionRequest request) {
        return client.execute(LIST_USER_POSITION, request, UserPositionResponse.class);
    }

    @Override
    public UserProjectResponse listUserProject(UserProjectRequest request) {
        return client.execute(LIST_USER_PROJECT, request, UserProjectResponse.class);
    }

    @Override
    public UserMenuResponse listUserMenu(UserMenuRequest request) {
        return client.execute(LIST_USER_MENU, request, UserMenuResponse.class);
    }

    @Override
    public UserPermissionResponse listUserPermission(UserPermissionRequest request) {
        return client.execute(LIST_USER_PERMISSION, request, UserPermissionResponse.class);
    }

}
