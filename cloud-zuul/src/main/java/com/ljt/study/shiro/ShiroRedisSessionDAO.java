package com.ljt.study.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * https://www.jianshu.com/p/4643715d1563
 *
 * @author LiJingTang
 * @date 2021-06-24 15:35
 */
@Slf4j
public class ShiroRedisSessionDAO extends AbstractSessionDAO {

    /**
     * key前缀
     */
    private static final String KEY_PREFIX = "shiro:session:";

    private String generateKey(Object key) {
        return KEY_PREFIX + this.getClass().getName() + "_" + key;
    }

    private final RedisTemplate<Object, Object> redisTemplate;

    private final ValueOperations<Object, Object> valueOperations;

    public ShiroRedisSessionDAO(RedisTemplate<Object, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.valueOperations = redisTemplate.opsForValue();
    }

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = generateSessionId(session);
        if (log.isDebugEnabled()) {
            log.debug("shiro redis session create. sessionId={}", sessionId);
        }
        assignSessionId(session, sessionId);
        valueOperations.set(generateKey(sessionId), session, session.getTimeout(), TimeUnit.MILLISECONDS);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        if (log.isDebugEnabled()) {
            log.debug("shiro redis session read. sessionId={}", sessionId);
        }
        return (Session) valueOperations.get(generateKey(sessionId));
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        if (log.isDebugEnabled()) {
            log.debug("shiro redis session update. sessionId={}", session.getId());
        }
        valueOperations.set(generateKey(session.getId()), session, session.getTimeout(), TimeUnit.MILLISECONDS);
    }

    @Override
    public void delete(Session session) {
        if (log.isDebugEnabled()) {
            log.debug("shiro redis session delete. sessionId={}", session.getId());
        }
        redisTemplate.delete(generateKey(session.getId()));
    }

    @Override
    public Collection<Session> getActiveSessions() {
        Set<Object> keySet = redisTemplate.keys(generateKey("*"));
        Set<Session> sessionSet = new HashSet<>();
        if (CollectionUtils.isEmpty(keySet)) {
            return Collections.emptySet();
        }
        for (Object key : keySet) {
            sessionSet.add((Session) valueOperations.get(key));
        }
        if (log.isDebugEnabled()) {
            log.debug("shiro redis session all. size={}", sessionSet.size());
        }
        return sessionSet;
    }

}
