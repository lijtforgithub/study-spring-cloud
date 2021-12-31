package com.ljt.study.rest;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author LiJingTang
 * @date 2021-12-29 16:38
 */
@Slf4j
//@Component
@Aspect
public class RestTemplateWrapperAspect {

    @Pointcut("target(com.ljt.study.rest.RestTemplateWrapper)")
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
