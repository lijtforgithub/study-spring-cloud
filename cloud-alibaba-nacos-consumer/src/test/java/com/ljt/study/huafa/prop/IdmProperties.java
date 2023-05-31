package com.ljt.study.huafa.prop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author LiJingTang
 * @date 2023-05-25 09:18
 */
@Data
@ConfigurationProperties(prefix = IdmProperties.PREFIX)
public class IdmProperties {

    public static final String PREFIX = "huafa.idm";

    private String url;
    private String base;
    private String username;
    private String password;

}
