package com.ljt.study.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author LiJingTang
 * @date 2021-06-07 15:10
 */
@Data
@Component
@ConfigurationProperties(prefix = "test")
public class TestProperties {

    private Set<String> sets = new HashSet<>();

    private Map<String, List<String>> map = new HashMap<>();

    private Map<String, Item> itemMap = new HashMap<>();


    @Data
    public static class Item {

        private String key;
        private String name;

    }

}
