package com.ljt.study.gray;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author LiJingTang
 * @date 2021-05-29 19:12
 */
@Configuration
public class GreyConfig {

    /**
     * @RibbonClient(value = ServiceApi.APP_NAME, configuration = GreyConfig.class)
     */

    @Bean
    public GreyAspect greyAspect() {
        return new GreyAspect();
    }

//    @Bean
//    public IRule rule() {
//        return new GreyRule();
//    }

}
