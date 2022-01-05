package com.ljt.study.rest;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author LiJingTang
 * @date 2021-12-29 16:38
 */
@Slf4j
@Aspect
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RestTemplateWrapperAspect {

    @Pointcut("within(com.ljt.study.rest.RestTemplateWrapper)")
    public void method() {}

    @SneakyThrows
    @Around("method()")
    public Object around(ProceedingJoinPoint joinPoint) {
        log.info("{} 方法开始", joinPoint.getSignature());
        try {
            Object result = joinPoint.proceed();
            log.info("{} 方法结束", joinPoint.getSignature());
            return result;
        } catch (Exception e) {
            log.error("方法异常 {}", e.getMessage());
            return null;
        }
    }

}
