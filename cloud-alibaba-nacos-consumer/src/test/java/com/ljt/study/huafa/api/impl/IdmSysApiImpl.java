package com.ljt.study.huafa.api.impl;

import cn.hutool.core.util.StrUtil;
import com.ljt.study.huafa.api.IdmSysApi;
import com.ljt.study.huafa.dto.idm.response.UserResponse;
import com.ljt.study.huafa.ldap.idm.model.IdmPerson;
import com.ljt.study.huafa.ldap.idm.service.IdmPersonService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

/**
 * @author LiJingTang
 * @date 2023-05-25 09:41
 */
public class IdmSysApiImpl implements IdmSysApi {

    @Autowired
    private IdmPersonService idmPersonService;

    @Override
    public UserResponse getById(String uid) {
        if (StrUtil.isBlank(uid)) {
            return null;
        }
        return getUser(idmPersonService.getOneById(uid));
    }

    @Override
    public UserResponse getByUsername(String username) {
        if (StrUtil.isBlank(username)) {
            return null;
        }
        return getUser(idmPersonService.getOneByUsername(username));
    }

    private UserResponse getUser(IdmPerson person) {
        if (Objects.isNull(person)) {
            return null;
        }

        UserResponse user = new UserResponse();
        user.setUid(person.getUid());
        user.setName(person.getCn());
        user.setUsername(person.getSmartAlias());
        user.setTitle(person.getTitle());
        return user;
    }

}
