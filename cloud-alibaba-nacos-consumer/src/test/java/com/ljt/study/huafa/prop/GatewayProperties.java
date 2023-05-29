package com.ljt.study.huafa.prop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author LiJingTang
 * @date 2023-05-19 20:48
 */
@Data
@Component
@ConfigurationProperties(prefix = GatewayProperties.PREFIX)
public class GatewayProperties {

    public static final String PREFIX = "huafa.gateway";

    private String appId;
    private String appSecret;

}
