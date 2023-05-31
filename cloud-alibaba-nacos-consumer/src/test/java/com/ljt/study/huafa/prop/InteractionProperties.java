package com.ljt.study.huafa.prop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author LiJingTang
 * @date 2023-05-21 10:20
 */
@Data
@ConfigurationProperties(prefix = InteractionProperties.PREFIX)
public class InteractionProperties {

    public static final String PREFIX = "huafa.interaction";

    private String url;

    private HttpClientProperties httpclient;

}
