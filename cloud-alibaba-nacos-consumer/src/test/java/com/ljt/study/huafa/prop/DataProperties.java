package com.ljt.study.huafa.prop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author LiJingTang
 * @date 2023-05-21 10:20
 */
@Data
@Component
@ConfigurationProperties(prefix = DataProperties.PREFIX)
public class DataProperties {

    public static final String PREFIX = "huafa.data";

    private String url;
    private String accessToken;

    private HttpClientProperties httpclient;

}
