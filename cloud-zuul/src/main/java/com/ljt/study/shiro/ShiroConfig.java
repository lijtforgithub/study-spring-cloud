package com.ljt.study.shiro;

import com.google.common.collect.Lists;
import org.apache.shiro.authc.pam.FirstSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LiJingTang
 * @date 2021-06-23 13:58
 */
@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(securityManager);
        factoryBean.setLoginUrl("/login");
        factoryBean.setUnauthorizedUrl("/login");

        Map<String, String> map = new LinkedHashMap<>();
        // authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问
//        map.put("/login", anon.name());
//        map.put("/api/**", authc.name());
        // 主要这行代码必须放在所有权限设置的最后，不然会导致所有url 都被拦截 剩余的都需要认证
//        map.put("/**", authc.name());

        factoryBean.setFilterChainDefinitionMap(map);
        return factoryBean;
    }

    @Bean
    public DefaultWebSecurityManager securityManager(SessionManager sessionManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setSessionManager(sessionManager);
        List<Realm> realms = Lists.newArrayList(customRealm());

        FirstSuccessfulStrategy strategy = new FirstSuccessfulStrategy();
        strategy.setStopAfterFirstSuccess(true);
        ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
        authenticator.setAuthenticationStrategy(strategy);
        authenticator.setRealms(realms);
        securityManager.setAuthenticator(authenticator);

        return securityManager;
    }

    @Bean
    public SessionManager sessionManager(SessionDAO sessionDAO) {
        ShiroSessionManager sessionManager = new ShiroSessionManager();
        sessionManager.setSessionDAO(sessionDAO);
        sessionManager.setSessionListeners(Collections.singletonList(sessionListener()));
        return sessionManager;
    }

    private SessionListener sessionListener() {
        return new ShiroSessionListener();
    }

    @Bean
    public SessionDAO sessionDAO(RedisTemplate<Object, Object> redisTemplate) {
        return new ShiroRedisSessionDAO(redisTemplate);
    }

    @Bean
    public CustomRealm customRealm() {
        return new CustomRealm();
    }

}
