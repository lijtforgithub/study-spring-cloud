package com.ljt.study.huafa.client;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.ljt.study.huafa.config.HttpClientConfig;
import com.ljt.study.huafa.enums.RequestEnum;
import com.ljt.study.huafa.enums.SystemEnum;
import com.ljt.study.huafa.prop.HttpClientProperties;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.HibernateValidator;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

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

        if (StrUtil.isNotBlank(getBaseUrl())) {
            DefaultUriBuilderFactory uriBuilder = new DefaultUriBuilderFactory(getBaseUrl());
            uriBuilder.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.TEMPLATE_AND_VALUES);
            restTemplate.setUriTemplateHandler(uriBuilder);
            log.info("{} 设置前缀地址 {}", getSystem(), getBaseUrl());
        }

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
        FastJsonHttpMessageConverter fastJsonMessageConverter = new FastJsonHttpMessageConverter();
        messageConverters.add(0, fastJsonMessageConverter);
        log.info("{} 使用fastjson", getSystem());
    }

    protected <RP> RP doExecute(E requestEnum, T req, Class<RP> clazz) {
        log.info("{} {}请求 {}", getSystem(), requestEnum.getMethod(), requestEnum.getUrl());

        StopWatch stopWatch = new StopWatch();
        try {
            validateParam(requestEnum, req, clazz, stopWatch);

            stopWatch.start("封装请求");
            String url = postUrl(requestEnum.getUrl());
            Object requestBody = null;
            if (HttpMethod.GET == requestEnum.getMethod()) {
                UriComponentsBuilder builder = UriComponentsBuilder.fromPath(url);
                MultiValueMap<String, String> query = RequestHelper.requestToQuery(req);
                builder.queryParams(query);
                url = builder.toUriString();
            } else {
                requestBody = getHttpBody(req);
            }
            HttpEntity<?> httpEntity = new HttpEntity<>(requestBody, getHttpHeader());
            stopWatch.stop();

            stopWatch.start("发起请求");
            ResponseEntity<String> str = restTemplate.exchange(url, requestEnum.getMethod(), httpEntity, String.class);
            ResponseEntity<RP> respEntity = restTemplate.exchange(url, requestEnum.getMethod(), httpEntity, clazz);
            stopWatch.stop();

            stopWatch.start("处理结果");
            Assert.isTrue(HttpStatus.OK == respEntity.getStatusCode(), () -> new RuntimeException("请求接口失败"));

            RP body = respEntity.getBody();

            try {
                handleResponse((R) body);
            } catch (ClassCastException e) {
                log.error("类转换异常", e);
            }
            stopWatch.stop();

            return body;
        } finally {
            log.info(stopWatch.prettyPrint());
        }
    }

    protected String postUrl(String url) {
        return url;
    }

    protected HttpHeaders getHttpHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    protected T getHttpBody(T req) {
        return req;
    }

    protected void handleResponse(R resp) {
        Assert.notNull(resp, "返回值为空");
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
        validateBean(req);
        stopWatch.stop();
    }


    private static final Validator VALIDATOR = Validation.byProvider(HibernateValidator.class).configure().failFast(true).buildValidatorFactory().getValidator();

    protected static <T> void validateBean(T t) {
        Set<ConstraintViolation<T>> violationSet = VALIDATOR.validate(t);
        if (CollUtil.isNotEmpty(violationSet)) {
            ConstraintViolation<T> violation = violationSet.iterator().next();
            throw new IllegalArgumentException(violation.getMessage());
        }
    }

}
