package com.ljt.study.huafa.ldap.idm.service;

import com.ljt.study.huafa.ldap.idm.enums.SmartTypeEnum;
import com.ljt.study.huafa.ldap.idm.model.IdmPerson;

import java.util.List;

/**
 * @author LiJingTang
 * @date 2023-05-25 09:47
 */
public interface IdmPersonService {

    IdmPerson getOneById(String uid);

    IdmPerson getOneByUsername(String username);

    /**
     * 根据条件查询IDM用户
     *
     * @param typeEnum 类型
     * @param valid 有效的 null-全部 true-启用状态 -false-禁用状态
     */
    List<IdmPerson> findList(SmartTypeEnum typeEnum, Boolean valid);

}
