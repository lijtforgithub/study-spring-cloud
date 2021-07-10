package com.ljt.study;

import com.netflix.hystrix.strategy.concurrency.HystrixContextRunnable;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariableDefault;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author LiJingTang
 * @date 2021-07-10 15:57
 */
class HystrixRequestContextTest {

    @BeforeEach
    void beforeEach() {
        HystrixRequestContext.initializeContext();
    }

    @AfterEach
    void afterEach() {
        HystrixRequestContext.getContextForCurrentThread().shutdown();
    }

    private void set(HystrixRequestVariableDefault<String> variableDefault) {
        variableDefault.set("我是" + Thread.currentThread().getName() + "的值");
    }

    @Test
    @SneakyThrows
    void subThread() {
        CountDownLatch latch = new CountDownLatch(1);
        HystrixRequestVariableDefault<String> variableDefault = new HystrixRequestVariableDefault<>();
        set(variableDefault);
        HystrixContextRunnable runnable = new HystrixContextRunnable(() -> {
            System.out.println(("子线程获取主线程的值：" + variableDefault.get()));
            latch.countDown();
        });
        new Thread(runnable).start();
        latch.await();
    }

    private static ExecutorService executor;

    static {
        executor = Executors.newFixedThreadPool(4);
        ((ThreadPoolExecutor) executor).prestartAllCoreThreads();
    }

    @Test
    @SneakyThrows
    void threadPool() {
        CountDownLatch latch = new CountDownLatch(1);
        HystrixRequestVariableDefault<String> variableDefault = new HystrixRequestVariableDefault<>();
        set(variableDefault);
        HystrixContextRunnable runnable = new HystrixContextRunnable(() -> {
            System.out.println(Thread.currentThread().getName() + " 线程池获取主线程的值：" + variableDefault.get());
            latch.countDown();
        });
        executor.submit(runnable);
        latch.await();
    }

}
