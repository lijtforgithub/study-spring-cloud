package com.ljt.study.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.session.mgt.WebSessionKey;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author LiJingTang
 * @date 2021-06-24 16:30
 */
@Slf4j
public class ShiroSessionManager extends DefaultWebSessionManager {

    /**
     * 获取session
     * 优化单次请求需要多次访问redis的问题
     */
    @Override
    protected Session retrieveSession(SessionKey sessionKey) throws UnknownSessionException {
        Serializable sessionId = getSessionId(sessionKey);
        ServletRequest request = null;

        if (sessionKey instanceof WebSessionKey) {
            request = ((WebSessionKey) sessionKey).getServletRequest();

            HttpServletRequest servletRequest = (HttpServletRequest) request;
            if ("/favicon.ico".equals(servletRequest.getRequestURI())) {
                log.info("真实应用要过滤静态资源");
                return null;
            }
        }

        final boolean flag = Objects.nonNull(request) && Objects.nonNull(sessionId);

        if (flag) {
            Object sessionObj = request.getAttribute(sessionId.toString());
            if (Objects.nonNull(sessionObj)) {
                log.debug("read session from request");
                return (Session) sessionObj;
            }
        }

        Session session = super.retrieveSession(sessionKey);
        if (flag) {
            request.setAttribute(sessionId.toString(), session);
        }
        return session;
    }

}
