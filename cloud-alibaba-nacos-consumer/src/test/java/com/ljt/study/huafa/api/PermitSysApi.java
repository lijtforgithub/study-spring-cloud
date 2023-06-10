package com.ljt.study.huafa.api;

import com.ljt.study.huafa.dto.permit.request.UserPositionRequest;
import com.ljt.study.huafa.dto.permit.request.UserProjectRequest;
import com.ljt.study.huafa.dto.permit.response.UserPositionResponse;
import com.ljt.study.huafa.dto.permit.response.UserProjectResponse;

/**
 * @author LiJingTang
 * @date 2023-05-22 16:03
 */
public interface PermitSysApi {

    /**
     * 用户岗位（按客户端）
     */
    UserPositionResponse listUserPosition(UserPositionRequest request);

    UserProjectResponse listUserProject(UserProjectRequest request);

}
