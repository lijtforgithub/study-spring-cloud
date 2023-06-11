package com.ljt.study.huafa.prop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author LiJingTang
 * @date 2023-05-23 19:01
 */
@Data
@ConfigurationProperties(prefix = OAProperties.PREFIX)
public class OAProperties {

    public static final String PREFIX = "huafa.oa";

    private String url;
    private String username;
    private String password;
    private String systemName;

    private HttpClientProperties httpclient;
    private FtpProperties ftp;


    @Data
    public static class FtpProperties {

        private String host;
        private Integer port;
        private String username;
        private String password;
        private String workDir;

    }

}
