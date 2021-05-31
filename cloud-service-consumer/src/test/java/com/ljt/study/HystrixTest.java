package com.ljt.study;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author LiJingTang
 * @date 2020-07-01 17:28
 */
public class HystrixTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Future<String> future = new HystrixDemo(HystrixCommandGroupKey.Factory.asKey("ext")).queue();
        System.out.println(future.get());
    }


    static class HystrixDemo extends HystrixCommand<String> {

        protected HystrixDemo(HystrixCommandGroupKey group) {
            super(group);
        }

        @Override
        protected String run() {
            int i = 1 / 0;
            return "OK";
        }

        @Override
        protected String getFallback() {
            return "fallback";
        }

    }

}
