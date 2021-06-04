package com.ljt.study.cloud.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 新奇写法
 *
 * @author LiJingTang
 * @date 2021-06-01 19:48
 */
@Data
@Component
@PropertySource(value = "classpath:url-mapping.yml", factory = YamlPropertySourceFactory.class)
@ConfigurationProperties(prefix = "pp")
public class UrlMappingProperties {

    /**
     * 如果不指定 YamlPropertySourceFactory
     * 这样写才能取到值 @Value("${file-name}")
     */
    private String fileName;

    /**
     * 黑名单
     */
    private Set<String> blackList = new HashSet<>();
    /**
     * 白名单
     */
    private Set<String> whiteList = new HashSet<>();
    /**
     * 自由端点
     */
    private Set<String> freeList = new HashSet<>();

    /**
     * 特殊跳转的url
     */
    private List<Item> redirectList;

    @Data
    public static class Item {
        private String origin;
        private String redirect;
        private String serviceId;
    }

}
