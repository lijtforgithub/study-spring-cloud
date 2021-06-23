package com.ljt.study.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

/**
 * @author LiJingTang
 * @date 2021-06-23 15:44
 */
@Slf4j
public class ShiroUtils {

    public static void login() {
        log.info("开始shiro认证");
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken();
        token.setUsername("测试shiro");
        subject.login(token);
    }

    public static void logout() {
        log.info("开始shiro登出");
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
    }

    public static boolean isLogin() {
        Subject subject = SecurityUtils.getSubject();
        return subject.isAuthenticated();
    }

}
