package com.ljt.study.huafa.config;

import com.ljt.study.huafa.api.*;
import com.ljt.study.huafa.api.impl.*;
import com.ljt.study.huafa.client.*;
import com.ljt.study.huafa.enums.SystemEnum;
import com.ljt.study.huafa.prop.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.integration.ftp.session.DefaultFtpsSessionFactory;
import org.springframework.integration.ftp.session.FtpRemoteFileTemplate;

import java.nio.charset.StandardCharsets;

import static org.apache.commons.net.ftp.FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE;

/**
 * @author LiJingTang
 * @date 2023-05-22 22:33
 */
@Slf4j
@Configuration
class SystemConfig {

    @Configuration
    @ConditionalOnProperty(prefix = "huafa.oa", value = "enabled", matchIfMissing = true)
    @EnableConfigurationProperties(OAProperties.class)
    static class OAConfig {

        @Autowired
        private OAProperties oaProperties;

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
            return new OAFtpClient(oaFtpFileTemplate());
        }

        @Bean
        FtpRemoteFileTemplate oaFtpFileTemplate() {
            FtpRemoteFileTemplate template = new FtpRemoteFileTemplate(oaFtpsSessionFactory());
            template.setRemoteDirectoryExpression(new LiteralExpression(oaProperties.getFtp().getWorkDir()));
            return template;
        }

        @Bean
        DefaultFtpsSessionFactory oaFtpsSessionFactory() {
            DefaultFtpsSessionFactory factory = new DefaultFtpsSessionFactory();
            factory.setHost(oaProperties.getFtp().getHost());
            factory.setPort(oaProperties.getFtp().getPort());
            factory.setUsername(oaProperties.getFtp().getUsername());
            factory.setPassword(oaProperties.getFtp().getPassword());
            factory.setProtocol("SSL");
            factory.setImplicit(true);
            factory.setConnectTimeout(10_000);
            factory.setControlEncoding(StandardCharsets.UTF_8.name());
            factory.setClientMode(PASSIVE_LOCAL_DATA_CONNECTION_MODE);
            return factory;
        }
    }

    @Configuration
    @ConditionalOnProperty(prefix = "huafa.data", value = "enabled", matchIfMissing = true)
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
    @ConditionalOnProperty(prefix = "huafa.customer", value = "enabled", matchIfMissing = true)
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
    @ConditionalOnProperty(prefix = "huafa.interaction", value = "enabled", matchIfMissing = true)
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
    @ConditionalOnProperty(prefix = "huafa.permit", value = "enabled", matchIfMissing = true)
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
