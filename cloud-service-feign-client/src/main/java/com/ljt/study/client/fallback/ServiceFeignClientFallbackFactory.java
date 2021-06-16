package com.ljt.study.client.fallback;

import com.ljt.study.client.ServiceFeignClient;
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
    public ServiceFeignClient create(Throwable e) {
        e.printStackTrace();

        /*
         * 兜底数据
         */
        return new ServiceFeignClient() {

            @Override
            public String getCurrentTime() {
                return getFallback(e);
            }

            @Override
            public String getServicePort() {
                return getFallback(e);
            }
        };
    }

    private String getFallback(Throwable e) {
        if (e instanceof FeignException.InternalServerError) {
            return "远程服务错误 " + e.getMessage();
        }

        return "服务降级了";
    }

}
