package com.ljt.study.huafa.config;

import com.ljt.study.huafa.api.*;
import com.ljt.study.huafa.api.impl.*;
import com.ljt.study.huafa.client.*;
import com.ljt.study.huafa.enums.SystemEnum;
import com.ljt.study.huafa.prop.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
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
    @ConditionalOnProperty(prefix = ClinkProperties.PREFIX, value = "enabled", matchIfMissing = true)
    @EnableConfigurationProperties(ClinkProperties.class)
    static class ClinkConfig {
        @Bean
        @ConditionalOnMissingBean
        ClinkSysApi clinkSysApi() {
            log.info("加载{}接口", SystemEnum.CLINK.getDesc());
            return new ClinkSysApiImpl(clinkHttpClient());
        }

        @Bean
        ClinkHttpClient clinkHttpClient() {
            return new ClinkHttpClient();
        }
    }

    @Configuration
    @ConditionalOnProperty(prefix = OAProperties.PREFIX, value = "enabled", matchIfMissing = true)
    @EnableConfigurationProperties(OAProperties.class)
    static class OAConfig {

        @Bean
        @ConditionalOnMissingBean
        OASysApi oaSysApi() {
            log.info("加载{}接口", SystemEnum.OA.getDesc());
            return new OASysApiImpl(oaHttpClient());
        }

        @Bean
        OAHttpClient oaHttpClient() {
            return new OAHttpClient();
        }

        @Bean
        OAFtpClient oaFtpClient() {
            return new OAFtpClient();
        }
    }

    @Configuration
    @ConditionalOnProperty(prefix = DataProperties.PREFIX, value = "enabled", matchIfMissing = true)
    @EnableConfigurationProperties(DataProperties.class)
    static class DataConfig {

        @Bean
        @ConditionalOnMissingBean
        DataSysApi dataSysApi() {
            log.info("加载{}接口", SystemEnum.DATA.getDesc());
            return new DataSysApiImpl(dataHttpClient());
        }

        @Bean
        DataHttpClient dataHttpClient() {
            return new DataHttpClient();
        }
    }


    @Configuration
    @ConditionalOnProperty(prefix = CustomerProperties.PREFIX, value = "enabled", matchIfMissing = true)
    @EnableConfigurationProperties(CustomerProperties.class)
    static class CustomerConfig {

        @Bean
        @ConditionalOnMissingBean
        CustomerSysApi customerSysApi() {
            log.info("加载{}接口", SystemEnum.CUSTOMER.getDesc());
            return new CustomerSysApiImpl(customerHttpClient());
        }

        @Bean
        CustomerHttpClient customerHttpClient() {
            return new CustomerHttpClient();
        }
    }


    @Configuration
    @ConditionalOnProperty(prefix = InteractionProperties.PREFIX, value = "enabled", matchIfMissing = true)
    @EnableConfigurationProperties(InteractionProperties.class)
    static class InteractionConfig {

        @Bean
        @ConditionalOnMissingBean
        InteractionSysApi interactionSysApi() {
            log.info("加载{}接口", SystemEnum.INTERACTION.getDesc());
            return new InteractionSysApiImpl(interactionHttpClient());
        }

        @Bean
        InteractionHttpClient interactionHttpClient() {
            return new InteractionHttpClient();
        }
    }


    @Configuration
    @ConditionalOnProperty(prefix = PermitProperties.PREFIX, value = "enabled", matchIfMissing = true)
    @EnableConfigurationProperties(PermitProperties.class)
    static class PermitConfig {

        @Bean
        @ConditionalOnMissingBean
        PermitSysApi permitSysApi() {
            log.info("加载{}接口", SystemEnum.PERMIT.getDesc());
            return new PermitSysApiImpl(permitHttpClient());
        }

        @Bean
        PermitHttpClient permitHttpClient() {
            return new PermitHttpClient();
        }
    }

}
