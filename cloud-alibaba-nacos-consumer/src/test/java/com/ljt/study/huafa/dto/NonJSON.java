package com.ljt.study.huafa.dto;

import java.lang.annotation.*;

/**
 * 非json返回标识
 *
 * 有此注解的类必须要有一个参数为String的构造方法
 *
 * @author LiJingTang
 * @date 2023-06-11 13:57
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NonJSON {

}
