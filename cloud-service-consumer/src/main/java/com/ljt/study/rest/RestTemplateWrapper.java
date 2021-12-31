package com.ljt.study.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.Collections;

/**
 * @see RestTemplateBuilder
 *
 * @author LiJingTang
 * @date 2021-12-29 10:12
 */
@Slf4j
@Getter
public class RestTemplateWrapper extends RestTemplate {

    static final String ACCESS_TOKEN = "access_token";
    static final String REFRESH_TOKEN = "refresh_token";
    static final String URL_TOKEN = "/auth/oauth/token";

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
    }

    public TokenDTO getToken() {
        String path = UriComponentsBuilder.fromPath(URL_TOKEN).queryParams(buildTokenReq()).build().toUriString();
        return postForObject(path, null, TokenDTO.class);
    }

    private MultiValueMap<String, String> buildTokenReq() {
        MultiValueMap<String, String> param = new LinkedMultiValueMap<>(4);
        param.put("grant_type", Collections.singletonList("client_credentials"));
        param.put("client_id", Collections.singletonList(clientId));
        param.put("client_secret", Collections.singletonList(clientSecret));
        param.put("state_code", Collections.singletonList(centerCityCode));
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
