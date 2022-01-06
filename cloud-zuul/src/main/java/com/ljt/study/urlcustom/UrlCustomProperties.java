package com.ljt.study.urlcustom;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;
import java.util.Set;

import static com.ljt.study.urlcustom.UrlCustomProperties.PREFIX;

/**
 * @author LiJingTang
 * @date 2021-12-21 14:01
 */
@Data
@ConfigurationProperties(prefix = PREFIX)
public class UrlCustomProperties {

    static final String PREFIX = "url-custom";

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
