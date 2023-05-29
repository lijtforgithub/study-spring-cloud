package com.ljt.study.huafa.config;

import cn.hutool.core.util.ObjectUtil;
import com.ljt.study.huafa.prop.HttpClientProperties;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

/**
 * @author LiJingTang
 * @date 2023-05-20 13:07
 */
@Configuration
public class HttpClientConfig {

    @Autowired
    private HttpClientProperties clientProperties;

    @Bean
    public ClientHttpRequestFactory clientRequestFactory(HttpClientProperties properties) {
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(ObjectUtil.defaultIfNull(properties.getSocketTimeout(), clientProperties.getSocketTimeout()))
                .setConnectTimeout(ObjectUtil.defaultIfNull(properties.getConnectTimeout(), clientProperties.getConnectTimeout()))
                .setConnectionRequestTimeout(ObjectUtil.defaultIfNull(properties.getConnectionRequestTimeout(), clientProperties.getConnectionRequestTimeout()))
                .build();
        HttpClient httpClient = HttpClientBuilder.create()
                .setMaxConnPerRoute(ObjectUtil.defaultIfNull(properties.getMaxConnPerRoute(), clientProperties.getMaxConnPerRoute()))
                .setMaxConnTotal(ObjectUtil.defaultIfNull(properties.getMaxConnTotal(), clientProperties.getMaxConnTotal()))
                .setDefaultRequestConfig(requestConfig).build();
        return new HttpComponentsClientHttpRequestFactory(httpClient);
    }

}
