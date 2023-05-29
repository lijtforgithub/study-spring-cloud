package com.ljt.study.huafa.api;

import com.ljt.study.huafa.dto.idm.response.UserResponse;

/**
 * @author LiJingTang
 * @date 2023-05-25 09:38
 */
public interface IdmSysApi {

    UserResponse getOneById(String uid);

    UserResponse getOneByUsername(String username);

}
