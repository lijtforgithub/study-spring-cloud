package com.ljt.study.huafa.ldap.idm.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.ljt.study.huafa.ldap.idm.enums.SmartTypeEnum;
import com.ljt.study.huafa.ldap.idm.model.IdmPerson;
import com.ljt.study.huafa.ldap.idm.service.IdmPersonService;
import com.ljt.study.huafa.ldap.util.AttributeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.ContainerCriteria;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.ldap.query.LdapQueryBuilder;
import org.springframework.util.CollectionUtils;

import javax.naming.directory.Attributes;
import java.util.List;
import java.util.Objects;

import static org.springframework.ldap.query.LdapQueryBuilder.query;
import static org.springframework.ldap.query.SearchScope.SUBTREE;

/**
 * @author LiJingTang
 * @date 2023-05-25 09:48
 */
@RequiredArgsConstructor
public class IdmPersonServiceImpl implements IdmPersonService {

    private static final String BASE_DN = "ou=People,o=huafa.com,o=isp";

    private final LdapTemplate ldapTemplate;

    @Override
    public IdmPerson getOneById(String uid) {
        if (StrUtil.isBlank(uid)) {
            return null;
        }

        LdapQuery query = query().base(BASE_DN).countLimit(1).searchScope(SUBTREE).where("uid").is(uid);
        List<IdmPerson> list = ldapTemplate.search(query, new PersonAttributesMapper());
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public IdmPerson getOneByUsername(String username) {
        if (StrUtil.isBlank(username)) {
            return null;
        }
        LdapQuery query = query().base(BASE_DN).countLimit(1).searchScope(SUBTREE).where("smart-alias").is(username);
        List<IdmPerson> list = ldapTemplate.search(query, new PersonAttributesMapper());
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<IdmPerson> findList(SmartTypeEnum typeEnum, Boolean valid) {
        Assert.notNull(typeEnum, "类型枚举为空");
        LdapQueryBuilder builder = query().base(BASE_DN).searchScope(SUBTREE);

        ContainerCriteria query;
        if (SmartTypeEnum.ALL == typeEnum) {
            query = builder.where("smart-type").like(typeEnum.getValue());
        } else {
            query = builder.where("smart-type").is(typeEnum.getValue());
        }

        if (Objects.nonNull(valid)) {
            query.and("smart-status").is(valid ? "1" : "0");
        }
        return ldapTemplate.search(query, new PersonAttributesMapper());
    }

    private static class PersonAttributesMapper implements AttributesMapper<IdmPerson> {

        @Override
        public IdmPerson mapFromAttributes(Attributes attrs) {
            return AttributeUtils.mapFromAttributes(attrs, IdmPerson.class);
        }
    }

}
