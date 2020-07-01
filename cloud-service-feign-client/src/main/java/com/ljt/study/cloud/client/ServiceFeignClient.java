package com.ljt.study.cloud.client;

import com.ljt.study.cloud.api.ServiceApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author LiJingTang
 * @date 2020-07-01 10:42
 */
@FeignClient(value = "cloud-service-provider", fallbackFactory = ServiceFeignClientFallbackFactory.class)
public interface ServiceFeignClient extends ServiceApi {
}
