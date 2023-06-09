package com.ljt.study.huafa.dto;

import java.lang.annotation.*;

/**
 * @author LiJingTang
 * @date 2023-06-04 18:09
 */
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface QueryParam {

    String value() default "";

}
