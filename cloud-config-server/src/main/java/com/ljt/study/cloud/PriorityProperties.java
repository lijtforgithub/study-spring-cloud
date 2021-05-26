package com.ljt.study.cloud;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author LiJingTang
 * @date 2021-05-26 10:44
 */
@Data
@Component
@ConfigurationProperties(prefix = "priority")
public class PriorityProperties {

    private String bootStrap;

    private String application;

    private String remote;

    private String arg;


}
