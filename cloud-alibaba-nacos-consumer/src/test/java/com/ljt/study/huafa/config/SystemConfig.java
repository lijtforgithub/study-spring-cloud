package com.ljt.study.huafa.config;

import com.ljt.study.huafa.api.*;
import com.ljt.study.huafa.api.impl.*;
import com.ljt.study.huafa.client.*;
import com.ljt.study.huafa.enums.SystemEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author LiJingTang
 * @date 2023-05-22 22:33
 */
@Slf4j
@Configuration
class SystemConfig {

    @Configuration
    @ConditionalOnProperty(prefix = "huafa.oa", value = "enabled", matchIfMissing = true)
    static class OAConfig {

        @Bean
        @ConditionalOnMissingBean
        OAHttpClient oaHttpClient() {
            return new OAHttpClient();
        }

        @Bean
        @ConditionalOnMissingBean
        OASysApi oaSysApi() {
            log.info("加载{}接口", SystemEnum.OA.getDesc());
            return new OASysApiImpl(oaHttpClient());
        }
    }

    @Configuration
    @ConditionalOnProperty(prefix = "huafa.data", value = "enabled", matchIfMissing = true)
    static class DataConfig {

        @Bean
        @ConditionalOnMissingBean
        DataHttpClient dataHttpClient() {
            return new DataHttpClient();
        }

        @Bean
        @ConditionalOnMissingBean
        DataSysApi dataSysApi() {
            log.info("加载{}接口", SystemEnum.DATA.getDesc());
            return new DataSysApiImpl(dataHttpClient());
        }
    }


    @Configuration
    @ConditionalOnProperty(prefix = "huafa.customer", value = "enabled", matchIfMissing = true)
    static class CustomerConfig {

        @Bean
        @ConditionalOnMissingBean
        CustomerHttpClient customerHttpClient() {
            return new CustomerHttpClient();
        }

        @Bean
        @ConditionalOnMissingBean
        CustomerSysApi customerSysApi() {
            log.info("加载{}接口", SystemEnum.CUSTOMER.getDesc());
            return new CustomerSysApiImpl(customerHttpClient());
        }
    }


    @Configuration
    @ConditionalOnProperty(prefix = "huafa.interaction", value = "enabled", matchIfMissing = true)
    static class InteractionConfig {

        @Bean
        @ConditionalOnMissingBean
        InteractionHttpClient interactionHttpClient() {
            return new InteractionHttpClient();
        }

        @Bean
        @ConditionalOnMissingBean
        InteractionSysApi interactionSysApi() {
            log.info("加载{}接口", SystemEnum.INTERACTION.getDesc());
            return new InteractionSysApiImpl(interactionHttpClient());
        }
    }


    @Configuration
    @ConditionalOnProperty(prefix = "huafa.permit", value = "enabled", matchIfMissing = true)
    static class PermitConfig {

        @Bean
        @ConditionalOnMissingBean
        PermitHttpClient permitHttpClient() {
            return new PermitHttpClient();
        }

        @Bean
        @ConditionalOnMissingBean
        PermitSysApi permitSysApi() {
            log.info("加载{}接口", SystemEnum.PERMIT.getDesc());
            return new PermitSysApiImpl(permitHttpClient());
        }
    }

}
