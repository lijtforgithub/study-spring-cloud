package com.ljt.study.huafa.api;

import com.ljt.study.huafa.dto.permit.request.UserMenuRequest;
import com.ljt.study.huafa.dto.permit.request.UserPermissionRequest;
import com.ljt.study.huafa.dto.permit.request.UserPositionRequest;
import com.ljt.study.huafa.dto.permit.request.UserProjectRequest;
import com.ljt.study.huafa.dto.permit.response.UserMenuResponse;
import com.ljt.study.huafa.dto.permit.response.UserPermissionResponse;
import com.ljt.study.huafa.dto.permit.response.UserPositionResponse;
import com.ljt.study.huafa.dto.permit.response.UserProjectResponse;

/**
 * @author LiJingTang
 * @date 2023-05-22 16:03
 */
public interface PermitSysApi {

    /**
     * 用户岗位
     */
    UserPositionResponse listUserPosition(UserPositionRequest request);

    /**
     * 用户项目
     */
    UserProjectResponse listUserProject(UserProjectRequest request);

    /**
     * 用户菜单
     */
    UserMenuResponse listUserMenu(UserMenuRequest request);

    /**
     * 用户权限
     */
    UserPermissionResponse listUserPermission(UserPermissionRequest request);

}
