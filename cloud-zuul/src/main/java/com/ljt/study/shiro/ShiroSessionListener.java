package com.ljt.study.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

/**
 * @author LiJingTang
 * @date 2021-06-24 17:28
 */
@Slf4j
public class ShiroSessionListener implements SessionListener {

    @Override
    public void onStart(Session session) {
        log.info("会话创建：{}", session.getId());
    }

    @Override
    public void onStop(Session session) {
        log.info("会话退出：{}", session.getId());
    }

    @Override
    public void onExpiration(Session session) {
        log.info("会话过期：{}", session.getId());
    }

}
