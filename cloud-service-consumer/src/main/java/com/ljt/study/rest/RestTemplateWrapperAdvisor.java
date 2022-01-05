package com.ljt.study.rest;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.StaticMethodMatcherPointcutAdvisor;

import java.lang.reflect.Method;

/**
 * @author LiJingTang
 * @date 2021-06-26 09:51
 */
@Slf4j
//@Component
class RestTemplateWrapperAdvisor extends StaticMethodMatcherPointcutAdvisor {

    private static final long serialVersionUID = -1210382191888075322L;

    public RestTemplateWrapperAdvisor() {
        setAdvice(new RestTemplateWrapperInterceptor());
    }

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        return targetClass == RestTemplateWrapper.class;
    }


    @Slf4j
    private static class RestTemplateWrapperInterceptor implements MethodInterceptor {

        @Override
        public Object invoke(MethodInvocation mi) throws Throwable {
            log.info("{} 方法开始", mi.getMethod().getName());
            try {
                Object result = mi.proceed();
                log.info("{} 方法结束", mi.getMethod().getName());
                return result;
            } catch (Exception e) {
                log.error("方法异常 {}", e.getMessage());
                return null;
            }
        }

    }

}
