package com.ljt.study.config;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariableDefault;

/**
 * @author LiJingTang
 * @date 2021-07-12 17:51
 */
public class ContextHolder {

    public static final ThreadLocal<String> THREAD_LOCAL = new InheritableThreadLocal<>();

    public static final HystrixRequestVariableDefault<String> HYSTRIX_VARIABLE = new HystrixRequestVariableDefault<>();

}
