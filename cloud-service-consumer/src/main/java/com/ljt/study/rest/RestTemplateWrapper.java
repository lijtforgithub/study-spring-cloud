package com.ljt.study.rest;

import com.alibaba.fastjson.JSON;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.MultiLevelCacheBuilder;
import com.alicp.jetcache.embedded.CaffeineCacheBuilder;
import com.alicp.jetcache.redis.lettuce.RedisLettuceCacheBuilder;
import com.alicp.jetcache.support.JavaValueDecoder;
import com.alicp.jetcache.support.JavaValueEncoder;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Maps;
import io.lettuce.core.RedisClient;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.ljt.study.rest.ApiPathEnum.GET_TOKEN;
import static com.ljt.study.rest.RestConstants.*;

/**
 * @see RestTemplateBuilder
 *
 * @author LiJingTang
 * @date 2021-12-29 10:12
 */
@Slf4j
@Getter
public class RestTemplateWrapper extends RestTemplate {

    @Value("${icdss.url:http://172.31.223.128:19002/v2.2}")
    private String icdssUrl;
    @Value("${icdss.clientId:8fc075584e46d3b5ddfsfdkg9iwaemngf}")
    private String clientId;
    @Value("${icdss.clientSecret:31ef0413cbb5c9454611820d739c7d9}")
    private String clientSecret;
    @Value("${centerCityCode:000002}")
    private String centerCityCode;

    @Value("${restTemplate.ConnectTimeout:2000}")
    private int connectTimeout;
    @Value("${restTemplate.ReadTimeout:2000}")
    private int readTimeout;

    @Autowired
    private RedisClient defaultClient;
    private Cache<String, TokenDTO> redisCache;
    private Cache<String, TokenDTO> tokenCache;
    private MultiLevelCacheBuilder.MultiLevelCacheBuilderImpl cacheBuilder;

    @PostConstruct
    public void init() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(connectTimeout);
        factory.setReadTimeout(readTimeout);
        this.setRequestFactory(factory);

        DefaultUriBuilderFactory uriBuilder = new DefaultUriBuilderFactory(icdssUrl);
        uriBuilder.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.TEMPLATE_AND_VALUES);
        this.setUriTemplateHandler(uriBuilder);

        this.setInterceptors(Collections.singletonList(new RestTemplateInterceptor(this)));

        Cache<String, TokenDTO> localCache = CaffeineCacheBuilder.createCaffeineCacheBuilder()
                .addMonitor(event -> log.debug("local: {}", event.getClass().getSimpleName()))
                .buildCache();
        redisCache = RedisLettuceCacheBuilder.createRedisLettuceCacheBuilder()
                .addMonitor(event -> log.debug("redis: {}", event.getClass().getSimpleName()))
                .valueEncoder(JavaValueEncoder.INSTANCE)
                .valueDecoder(JavaValueDecoder.INSTANCE)
                .redisClient(defaultClient)
                .keyPrefix(CACHE_PREFIX)
                .buildCache();
        cacheBuilder = MultiLevelCacheBuilder.createMultiLevelCacheBuilder()
                .cachePenetrateProtect(true)
                .addCache(localCache, redisCache);
    }

    /**
     * 查询token
     */
    @Retryable(value = TokenException.class, backoff = @Backoff(500))
    public TokenDTO getToken() {
        if (Objects.isNull(tokenCache) || Objects.isNull(tokenCache.get(ACCESS_TOKEN))) {
            log.info("查询token");
            TokenDTO dto = executeForObject(GET_TOKEN, null, TokenDTO.class, buildTokenReq());
            writeCache(dto);
            log.info("查询token成功 {}", JSON.toJSONString(dto));
        }

        return tokenCache.get(ACCESS_TOKEN);
    }

    /**
     * token写入缓存
     */
    private synchronized void writeCache(TokenDTO dto) {
        if (Objects.isNull(dto) || StringUtils.isBlank(dto.getAccessToken())) {
            tokenCache.remove(ACCESS_TOKEN);
            tokenCache.remove(CACHE_LISTENER);
            throw new TokenException("Token为空");
        }
        tokenCache = cacheBuilder.expireAfterWrite(dto.getExpiresIn() - TimeUnit.MINUTES.toSeconds(1), TimeUnit.SECONDS).buildCache();
        tokenCache.put(ACCESS_TOKEN, dto);

        long expire = Math.max(1, dto.getExpiresIn() - TimeUnit.MINUTES.toSeconds(5));
        redisCache.put(CACHE_LISTENER, null, expire, TimeUnit.SECONDS);
        log.debug("token写入缓存");
    }

    /**
     * 刷新token
     */
    @Retryable(value = TokenException.class, backoff = @Backoff(500))
    public void refreshToken() {
        log.info("刷新token");
        TokenDTO dto = tokenCache.get(ACCESS_TOKEN);
        if (Objects.isNull(dto) || StringUtils.isBlank(dto.getAccessToken())) {
            throw new TokenException("缓存Token为空");
        }

        Map<String, String> param = buildTokenReq();
        param.put(ACCESS_TOKEN, dto.getAccessToken());
        param.put(REFRESH_TOKEN, dto.getRefreshToken());

        dto = executeForObject(GET_TOKEN, null, TokenDTO.class, param);
        writeCache(dto);
        log.info("刷新token成功 {}", JSON.toJSONString(dto));
    }

    /**
     * 封装使用 ApiPathEnum 为入参方法
     *
     * @param apiEnum 请求接口
     * @param requestEntity 请求体
     * @param responseType 响应体类型
     * @param uriVariables url参数
     * @param <T> 响应体类型
     * @return 响应数据
     */
    public <T> ResponseEntity<T> executeForEntity(ApiPathEnum apiEnum, HttpEntity<?> requestEntity, Class<T> responseType, Map<String, ?> uriVariables) {
        String path = getPath(apiEnum, uriVariables);
        return exchange(path, apiEnum.getMethod(), requestEntity, responseType);
    }

    /**
     * 转换url参数
     */
    private String getPath(ApiPathEnum apiEnum, Map<String, ?> uriVariables) {
        Assert.notNull(apiEnum, "请求枚举为空");
        String path = apiEnum.getPath();
        if (!CollectionUtils.isEmpty(uriVariables)) {
            MultiValueMap<String, String> param;
            if (uriVariables instanceof  MultiValueMap) {
                param = (MultiValueMap<String, String>) uriVariables;
            } else {
                param = new LinkedMultiValueMap<>(uriVariables.size());
                uriVariables.forEach((k, v) -> param.putIfAbsent(k, Collections.singletonList(String.valueOf(v))));
            }
            path = UriComponentsBuilder.fromUriString(path).queryParams(param).build().toUriString();
        }
        return path;
    }

    /**
     * 封装使用 ApiPathEnum 为入参方法
     *
     * @param apiEnum 请求接口
     * @param request 请求体
     * @param responseType 响应体类型
     * @param uriVariables url参数
     * @param <T> 响应体类型
     * @return 响应体类型
     */
    public <T> T executeForObject(ApiPathEnum apiEnum, Object request, Class<T> responseType, Map<String, ?> uriVariables) {
        String path = getPath(apiEnum, uriVariables);
        RequestCallback requestCallback = httpEntityCallback(request, responseType);
        HttpMessageConverterExtractor<T> responseExtractor = new HttpMessageConverterExtractor<>(responseType, getMessageConverters());
        return execute(path, HttpMethod.POST, requestCallback, responseExtractor, uriVariables);
    }

    /**
     * token请求参数
     */
    private Map<String, String> buildTokenReq() {
        Map<String, String> param = Maps.newHashMapWithExpectedSize(6);
        param.put("grant_type", "client_credentials");
        param.put("client_id", clientId);
        param.put("client_secret", clientSecret);
        param.put("state_code", centerCityCode);
        return param;
    }


    @Data
    public static class TokenDTO implements Serializable {
        private static final long serialVersionUID = 3897063817928917436L;

        @JsonProperty(ACCESS_TOKEN)
        private String accessToken;
        @JsonProperty(REFRESH_TOKEN)
        private String refreshToken;
        @JsonProperty("expires_in")
        private Long expiresIn;
        private String scope;
        @JsonProperty("token_type")
        private String tokenType;
    }

}
