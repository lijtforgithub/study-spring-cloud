package com.ljt.study.huafa.prop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author LiJingTang
 * @date 2023-06-15 14:19
 */
@Data
@ConfigurationProperties(prefix = MpmProperties.PREFIX)
public class MpmProperties {

    public static final String PREFIX = "huafa.mpm";

    private String url;
    private String sysKey;
    private String secret;

    private HttpClientProperties httpclient;

}
