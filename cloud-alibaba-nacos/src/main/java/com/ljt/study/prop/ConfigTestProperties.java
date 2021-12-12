package com.ljt.study.prop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author LiJingTang
 * @date 2021-12-12 11:46
 */
@Data
@Component
@ConfigurationProperties(prefix = "config")
public class ConfigTestProperties {

    private String global;
    private String app;
    private String env;

}
