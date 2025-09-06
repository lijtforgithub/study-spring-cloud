package com.ljt.study;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author LiJingTang
 * @date 2025-09-05 16:54
 */
@Data
@Component
@ConfigurationProperties(prefix = "test")
public class ConfigProperties {

    private String key;
    private List<String> list;

}
