package com.ljt.study.timeout;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;
import java.util.Set;

import static com.ljt.study.timeout.RibbonTimeoutProperties.PREFIX;

/**
 * @author LiJingTang
 * @date 2021-12-21 14:01
 */
@Data
@ConfigurationProperties(prefix = PREFIX)
public class RibbonTimeoutProperties {

    static final String PREFIX = "ribbon.custom";
    public static final String ENABLE = PREFIX + ".enable";

    private boolean enable = true;

    /**
     * 链接建立的时间
     */
    private int connectTimeout;
    /**
     * 等待数据的时间或者两个包之间的间隔时间
     */
    private int socketTimeout;

    private Map<String, Set<String>> urlMap;

}
