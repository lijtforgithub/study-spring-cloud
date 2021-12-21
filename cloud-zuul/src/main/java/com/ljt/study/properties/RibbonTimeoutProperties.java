package com.ljt.study.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

import static com.ljt.study.properties.RibbonTimeoutProperties.PREFIX;

/**
 * @author LiJingTang
 * @date 2021-12-21 14:01
 */
@Data
@Component
@PropertySource(value = "classpath:ribbon-timeout.yml", factory = YamlPropertySourceFactory.class)
@ConfigurationProperties(prefix = PREFIX)
public class RibbonTimeoutProperties {

    static final String PREFIX = "ribbon.custom";
    public static final String ENABLE = PREFIX + ".enable";

    private boolean enable = false;

    private int connectTimeout;
    private int socketTimeout;

    private Map<String, Set<String>> urlMap;

}
