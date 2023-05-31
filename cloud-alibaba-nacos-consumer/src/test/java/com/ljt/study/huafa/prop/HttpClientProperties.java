package com.ljt.study.huafa.prop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author LiJingTang
 * @date 2023-05-20 13:02
 */
@Data
@ConfigurationProperties(prefix = HttpClientProperties.PREFIX)
public class HttpClientProperties {

    public static final String PREFIX = "huafa.httpclient";

    private Integer socketTimeout = 100_000;
    private Integer connectTimeout = 30_000;
    private Integer connectionRequestTimeout = 30_000;
    private Integer maxConnPerRoute = 30;
    private Integer maxConnTotal = 100;

}
