package com.ljt.study.huafa.config;

import com.ljt.study.huafa.api.IdmSysApi;
import com.ljt.study.huafa.api.impl.IdmSysApiImpl;
import com.ljt.study.huafa.enums.SystemEnum;
import com.ljt.study.huafa.ldap.idm.service.IdmPersonService;
import com.ljt.study.huafa.ldap.idm.service.impl.IdmPersonServiceImpl;
import com.ljt.study.huafa.prop.IdmProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;


/**
 * @author LiJingTang
 * @date 2023-05-25 09:20
 */
@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "huafa.idm", value = "enabled", matchIfMissing = true)
@EnableConfigurationProperties(IdmProperties.class)
class LdapIdmConfig {

    @Bean
    @ConditionalOnMissingBean
    IdmSysApi idmSysApi() {
        return new IdmSysApiImpl();
    }

    @Configuration
    static class IdmConfig {

        @Autowired
        private IdmProperties idmProperties;

        @Bean
        IdmPersonService idmPersonService() {
            return new IdmPersonServiceImpl(idmLdapTemplate());
        }

        @Bean
        LdapContextSource idmContextSource() {
            LdapContextSource source = new LdapContextSource();
            source.setUserDn(idmProperties.getUsername());
            source.setPassword(idmProperties.getPassword());
            source.setBase(idmProperties.getBase());
            source.setUrls(new String[] {idmProperties.getUrl()});
            return source;
        }

        @Bean
        LdapTemplate idmLdapTemplate(){
            log.info("加载{}", SystemEnum.IDM.getDesc());
            return new LdapTemplate(idmContextSource());
        }
    }

}
