package com.ljt.study.cloud.client;

import feign.FeignException;
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
        cause.printStackTrace();
        /**
         * 兜底数据
         */
        return () -> {
            if (cause instanceof FeignException.InternalServerError) {
                return "远程服务错误 " + cause.getMessage();
            }
            return "服务降级了";
        };
    }

}
