package com.ljt.study.cloud.gray;

import io.jmnarloch.spring.cloud.ribbon.support.RibbonFilterContextHolder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

import static com.ljt.study.cloud.gray.GreyHelper.USER_ID;
import static com.ljt.study.cloud.gray.GreyHelper.VERSION;

/**
 * @author LiJingTang
 * @date 2021-05-29 18:18
 */
@Aspect
@Component
@ConditionalOnClass(GreyConfig.class)
public class GreyAspect {

    @Pointcut("execution(public * com.ljt.study.cloud.web.*.*(..))")
    public void webMethod() {
    }

    @Before("webMethod()")
    public void before(JoinPoint point) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        final String userId = request.getHeader(USER_ID);
        if (Objects.nonNull(userId)) {
//            GreyDTO dto = new GreyDTO();
//            dto.setUserId(Long.parseLong(userId));
//            GreyHelper.set(dto);

            RibbonFilterContextHolder.getCurrentContext().add(VERSION, Integer.parseInt(userId) <= 100 ? "v1" : "v2");
        } else {
            RibbonFilterContextHolder.getCurrentContext().remove(VERSION);
        }
    }

}
