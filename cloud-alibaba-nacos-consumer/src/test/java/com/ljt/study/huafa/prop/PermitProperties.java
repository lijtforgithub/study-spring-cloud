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
@ConfigurationProperties(prefix = PermitProperties.PREFIX)
public class PermitProperties {

    public static final String PREFIX = "huafa.permit";

    private String url;

    private HttpClientProperties httpclient;

}
