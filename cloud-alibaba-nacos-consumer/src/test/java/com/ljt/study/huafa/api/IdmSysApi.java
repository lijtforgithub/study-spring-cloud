package com.ljt.study.huafa.api;

import com.ljt.study.huafa.dto.idm.response.UserResponse;

/**
 * @author LiJingTang
 * @date 2023-05-25 09:38
 */
public interface IdmSysApi {

    /**
     * 根据ID查询用户
     */
    UserResponse getById(String uid);

    /**
     * 根据账号查询账户
     */
    UserResponse getByUsername(String username);

}
