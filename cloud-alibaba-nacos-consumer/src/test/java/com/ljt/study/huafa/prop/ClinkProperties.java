package com.ljt.study.huafa.prop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author LiJingTang
 * @date 2023-06-06 11:37
 */
@Data
@ConfigurationProperties(prefix = ClinkProperties.PREFIX)
public class ClinkProperties {

    public static final String PREFIX = "huafa.clink";

    private String url;
    private String accessKeyId;
    private String accessKeySecret;
    private String businessLine;
    private String platform;
    private String publicKey;

    private HttpClientProperties httpclient;

}
