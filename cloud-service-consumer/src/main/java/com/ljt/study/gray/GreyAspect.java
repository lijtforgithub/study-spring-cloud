package com.ljt.study.gray;

import io.jmnarloch.spring.cloud.ribbon.support.RibbonFilterContextHolder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @author LiJingTang
 * @date 2021-05-29 18:18
 */
@Aspect
class GreyAspect {

    @Pointcut("within(com.ljt.study.controller..*)")
    public void webMethod() {
    }

    @Before("webMethod()")
    public void before(JoinPoint point) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        final String userId = request.getHeader(GreyHelper.USER_ID);
        if (Objects.nonNull(userId)) {
            RibbonFilterContextHolder.getCurrentContext().add(GreyHelper.VERSION, Integer.parseInt(userId) <= 100 ? "v1" : "v2");
        } else {
            RibbonFilterContextHolder.getCurrentContext().remove(GreyHelper.VERSION);
        }

        /*if (Objects.nonNull(userId)) {
            GreyDTO dto = new GreyDTO();
            dto.setUserId(Long.parseLong(userId));
            GreyHelper.set(dto);
        } else {
            GreyHelper.remove();
        }*/
    }

}
