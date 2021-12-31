package com.ljt.study.urlcustom;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author LiJingTang
 * @date 2021-12-22 15:18
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(UrlCustomConfig.class)
@Documented
public @interface EnableUrlCustom {
}
