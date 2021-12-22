package com.ljt.study.timeout;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author LiJingTang
 * @date 2021-12-22 15:18
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({RibbonTimeoutConfig.class})
@Documented
public @interface EnableUrlRibbonTimeout {
}
