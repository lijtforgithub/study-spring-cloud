package com.ljt.study.cloud.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author LiJingTang
 * @date 2020-07-01 17:28
 */
@Slf4j
public class HystrixTest extends HystrixCommand {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Future<String> future = new HystrixTest(HystrixCommandGroupKey.Factory.asKey("ext")).queue();
        log.info(future.get());
    }

    protected HystrixTest(HystrixCommandGroupKey group) {
        super(group);
    }

    @Override
    protected Object run() {
        int i = 1 / 0;
        return "OK";
    }

    @Override
    protected Object getFallback() {
        return "getFallback";
    }

}
