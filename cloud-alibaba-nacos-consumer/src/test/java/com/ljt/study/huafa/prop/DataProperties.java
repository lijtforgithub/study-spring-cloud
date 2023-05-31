package com.ljt.study.huafa.prop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author LiJingTang
 * @date 2023-05-21 10:20
 */
@Data
@ConfigurationProperties(prefix = DataProperties.PREFIX)
public class DataProperties {

    public static final String PREFIX = "huafa.data";

    private String url;
    private String accessToken;

    private HttpClientProperties httpclient;

}
