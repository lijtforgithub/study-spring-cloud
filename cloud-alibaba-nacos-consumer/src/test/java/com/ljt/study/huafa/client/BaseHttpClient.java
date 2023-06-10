package com.ljt.study.huafa.client;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.ljt.study.huafa.config.HttpClientConfig;
import com.ljt.study.huafa.enums.RequestEnum;
import com.ljt.study.huafa.enums.SystemEnum;
import com.ljt.study.huafa.exception.ClientException;
import com.ljt.study.huafa.prop.HttpClientProperties;
import com.ljt.study.huafa.util.ValidatorUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StopWatch;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * @author LiJingTang
 * @date 2023-05-20 13:22
 */
@Slf4j
abstract class BaseHttpClient<E extends RequestEnum, T, R> {

    @Resource
    private ClientHttpRequestFactory clientRequestFactory;
    @Resource
    private HttpClientConfig httpClientConfig;

    protected RestTemplate restTemplate;

    @PostConstruct
    void init() {
        HttpClientProperties clientProperties = getHttpClientProperties();

        if (Objects.nonNull(clientProperties)) {
            log.info("{} 自定义httpclient参数 {}", getSystem(), clientProperties);
            clientRequestFactory = httpClientConfig.clientRequestFactory(clientProperties);
        }

        RestTemplate restTemplate = new RestTemplate(clientRequestFactory);
        this.restTemplate = restTemplate;

        List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
        Iterator<HttpMessageConverter<?>> iterator = messageConverters.iterator();
        if (iterator.hasNext()) {
            HttpMessageConverter<?> converter = iterator.next();
            // 原有的String是ISO-8859-1编码 去掉
            if (converter instanceof StringHttpMessageConverter) {
                iterator.remove();
            }
            // 由于系统中默认有jackson 在转换json时自动会启用  但是我们不想使用它 可以直接移除或者将fastjson放在首位
            if (converter instanceof GsonHttpMessageConverter || converter instanceof MappingJackson2HttpMessageConverter) {
                iterator.remove();
            }
        }
        messageConverters.add(new StringHttpMessageConverter(StandardCharsets.UTF_8));
        FastJsonHttpMessageConverter messageConverter = new FastJsonHttpMessageConverter();
        messageConverters.add(0, messageConverter);
        log.info("{} 使用fastjson", getSystem());
    }

    protected <RP> RP doExecute(E requestEnum, T req, Class<RP> clazz) {
        log.info("{} {}请求 {}", getSystem(), requestEnum.getMethod(), requestEnum.getUrl());

        StopWatch stopWatch = new StopWatch();
        try {
            validateParam(requestEnum, req, clazz, stopWatch);

            stopWatch.start("封装请求");
            MultiValueMap<String, String> query = initQueryParam(requestEnum, req);
            URI uri = processQueryParam(requestEnum, query);
            Object requestBody = processBodyParam(req);
            HttpEntity<?> httpEntity = new HttpEntity<>(requestBody, getHttpHeader());
            stopWatch.stop();

            stopWatch.start("发起请求");
            RP body = null;
            try {
                ResponseEntity<RP> respEntity = restTemplate.exchange(uri, requestEnum.getMethod(), httpEntity, clazz);
                body = respEntity.getBody();
            } catch (HttpClientErrorException e) {
                log.info("请求异常", e);
                handleHttpError(e);
            }
            stopWatch.stop();

            stopWatch.start("处理结果");
            try {
                handleResponse((R) body);
            } catch (ClassCastException e) {
                log.error("类转换异常", e);
            }
            stopWatch.stop();

            return body;
        } finally {
            if (stopWatch.isRunning()) {
                stopWatch.stop();
            }
            log.info(stopWatch.prettyPrint());
        }
    }

    protected HttpHeaders getHttpHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    protected URI processQueryParam(E requestEnum, MultiValueMap<String, String> query) {
        return UriComponentsBuilder.fromHttpUrl(getBaseUrl()).path(requestEnum.getUrl()).queryParams(query).build().toUri();
    }

    protected T processBodyParam(T req) {
        return req;
    }

    protected void handleResponse(R resp) {
        Assert.notNull(resp, () -> new ClientException("返回值为空"));
    }

    protected void handleHttpError(HttpClientErrorException e) {
        String msg = StrUtil.blankToDefault(e.getResponseBodyAsString(), e.getStatusText());
        throw new ClientException(e.getRawStatusCode(), msg);
    }

    protected String getBaseUrl() {
        return null;
    }

    protected abstract SystemEnum getSystem();

    protected abstract HttpClientProperties getHttpClientProperties();

    private void validateParam(E requestEnum, Object req, Class<?> clazz, StopWatch stopWatch) {
        stopWatch.start("入参校验");
        Assert.notNull(requestEnum, "请求枚举为空");
        Assert.notNull(req, "请求入参为空");
        Assert.notNull(clazz, "返回值类型为空");
        ValidatorUtils.validateBean(req);
        stopWatch.stop();
    }

    private MultiValueMap<String, String> initQueryParam(E requestEnum, T req) {
        MultiValueMap<String, String> query = new LinkedMultiValueMap<>();
        if (HttpMethod.GET == requestEnum.getMethod()) {
            MultiValueMap<String, String> map = RequestHelper.requestToQuery(req);
            if (Objects.nonNull(map)) {
                query.putAll(map);
            }
        } else {
            MultiValueMap<String, String> map = RequestHelper.requestExtractQuery(req);
            if (Objects.nonNull(map)) {
                query.putAll(map);
            }
        }

        return query;
    }

}
