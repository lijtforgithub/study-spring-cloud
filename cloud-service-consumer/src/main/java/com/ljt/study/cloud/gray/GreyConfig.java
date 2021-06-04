package com.ljt.study.cloud.gray;

/**
 * @author LiJingTang
 * @date 2021-05-29 19:12
 */
public class GreyConfig {

    /**
     * @RibbonClient(value = ServiceApi.APP_NAME, configuration = GreyConfig.class)
     * 此种方式 这里不生效 使用了一下方式
     * @Component
     * @ConditionalOnClass(GreyConfig.class)
     */
//    @Bean
//    public GreyAspect greyAspect() {
//        return new GreyAspect();
//    }

//    @Bean
//    public IRule rule() {
//        return new GreyRule();
//    }

}
