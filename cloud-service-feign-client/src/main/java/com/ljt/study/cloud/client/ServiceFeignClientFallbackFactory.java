package com.ljt.study.cloud.client;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author LiJingTang
 * @date 2020-07-01 10:45
 */
@Component
public class ServiceFeignClientFallbackFactory implements FallbackFactory<ServiceFeignClient> {

    @Override
    public ServiceFeignClient create(Throwable cause) {
        return () -> "接口调用失败";
    }

}
