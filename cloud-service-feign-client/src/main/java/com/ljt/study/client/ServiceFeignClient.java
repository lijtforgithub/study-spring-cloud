package com.ljt.study.client;

import com.ljt.study.api.ServiceApi;
import com.ljt.study.client.fallback.ServiceFeignClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author LiJingTang
 * @date 2020-07-01 10:42
 */
@FeignClient(value = ServiceApi.APP_NAME, fallbackFactory = ServiceFeignClientFallbackFactory.class)
public interface ServiceFeignClient extends ServiceApi {
}
