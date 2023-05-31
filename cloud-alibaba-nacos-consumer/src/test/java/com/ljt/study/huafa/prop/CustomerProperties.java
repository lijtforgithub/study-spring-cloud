package com.ljt.study.huafa.prop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author LiJingTang
 * @date 2023-05-19 20:48
 */
@Data
@ConfigurationProperties(prefix = CustomerProperties.PREFIX)
public class CustomerProperties {

    public static final String PREFIX = "huafa.customer";

    private String url;

    private HttpClientProperties httpclient;

}
