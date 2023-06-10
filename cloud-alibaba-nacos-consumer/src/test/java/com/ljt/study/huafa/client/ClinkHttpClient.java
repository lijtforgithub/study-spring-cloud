package com.ljt.study.huafa.client;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.HMac;
import cn.hutool.crypto.digest.HmacAlgorithm;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.ljt.study.huafa.dto.clink.ClinkBaseRequest;
import com.ljt.study.huafa.dto.clink.ClinkBaseResponse;
import com.ljt.study.huafa.enums.ClinkRequestEnum;
import com.ljt.study.huafa.enums.RequestEnum;
import com.ljt.study.huafa.enums.SystemEnum;
import com.ljt.study.huafa.exception.ClientException;
import com.ljt.study.huafa.exception.ClinkClientException;
import com.ljt.study.huafa.prop.ClinkProperties;
import com.ljt.study.huafa.prop.HttpClientProperties;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author LiJingTang
 * @date 2023-06-06 11:42
 */
@Slf4j
public class ClinkHttpClient extends BaseHttpClient<ClinkRequestEnum, ClinkBaseRequest, ClinkBaseResponse> {

    @Autowired
    private ClinkProperties clinkProperties;

    public <R extends ClinkBaseResponse> R execute(ClinkRequestEnum requestEnum, ClinkBaseRequest req, Class<R> clazz) {
        return super.doExecute(requestEnum, req, clazz);
    }


    @Override
    protected URI processQueryParam(ClinkRequestEnum requestEnum, MultiValueMap<String, String> query) {
        SignatureHelper helper = new SignatureHelper(requestEnum, clinkProperties, query);
        MultiValueMap<String, String> newQuery = helper.newQuery();

        try {
            /*
             * 防止URL再次编码带来的问题
             * UriComponentsBuilder.toUriString 会编码 build().encode(此方法有编码逻辑).toUriString()
             * UriComponents.toUriString 不会编码
             */
            return new URI(UriComponentsBuilder.fromHttpUrl(getBaseUrl()).path(requestEnum.getUrl()).queryParams(newQuery).build().toUriString());
        } catch (URISyntaxException e) {
            throw new ClientException(e.getMessage());
        }
    }

    @Override
    protected void handleHttpError(HttpClientErrorException e) {
        String message = e.getResponseBodyAsString();
        if (StrUtil.isNotBlank(message)) {
            try {
                ClinkBaseResponse response = JSON.parseObject(message, ClinkBaseResponse.class);
                if (Objects.nonNull(response.getError())) {
                    throw new ClinkClientException(e.getRawStatusCode(), message, response.getError());
                }
            } catch (JSONException ex) {
                log.warn(ex.getMessage());
            }
        }

        super.handleHttpError(e);
    }

    @Override
    protected SystemEnum getSystem() {
        return SystemEnum.CLINK;
    }

    @Override
    protected String getBaseUrl() {
        return clinkProperties.getUrl();
    }

    @Override
    protected HttpClientProperties getHttpClientProperties() {
        return clinkProperties.getHttpclient();
    }


    @Data
    public static class SignatureHelper {

        public static final String ACCESS_KEY_ID = "AccessKeyId";
        public static final String EXPIRES = "Expires";
        public static final String TIMESTAMP = "Timestamp";
        public static final String SIGNATURE = "Signature";

        private static final String EXPIRES_SECOND = "600";
        private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        private final String timestamp = OffsetDateTime.now().format(FORMATTER);

        private final RequestEnum requestEnum;
        private final ClinkProperties clinkProperties;
        private final MultiValueMap<String, String> query;

        public SignatureHelper(RequestEnum requestEnum, ClinkProperties clinkProperties, MultiValueMap<String, String> query) {
            this.requestEnum = requestEnum;
            this.clinkProperties = clinkProperties;
            this.query = query;

            this.query.add(ACCESS_KEY_ID, clinkProperties.getAccessKeyId());
            this.query.add(EXPIRES, EXPIRES_SECOND);
            this.query.add(TIMESTAMP, timestamp);
        }

        public MultiValueMap<String, String> newQuery() {
            List<String> keys = query.keySet().stream().sorted(Comparator.naturalOrder()).collect(Collectors.toList());
            MultiValueMap<String, String> newQuery = new LinkedMultiValueMap<>();
            keys.forEach(key -> {
                String k = encode(key);
                query.get(key).forEach(value -> {
                    newQuery.add(k, encode(value));
                });
            });

            UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(clinkProperties.getUrl()).path(requestEnum.getUrl()).queryParams(newQuery).build();
            int index = 0;
            String scheme = uriComponents.getScheme();
            if (Objects.nonNull(scheme)) {
                index = scheme.length() + 3;
            }
            String urlParam = requestEnum.getMethod().name() + uriComponents.toUriString().substring(index);
            String sign = encode(sign(urlParam));

            newQuery.add(SIGNATURE, sign);

            return newQuery;
        }

        private String sign(String str) {
            byte[] key = clinkProperties.getAccessKeySecret().getBytes(StandardCharsets.UTF_8);
            HMac mac = new HMac(HmacAlgorithm.HmacSHA1, key);
            return mac.digestBase64(str, StandardCharsets.UTF_8, false);
        }

        private static String encode(String str) {
            try {
                return URLEncoder.encode(str, StandardCharsets.UTF_8.name());
            } catch (UnsupportedEncodingException e) {
                log.warn("URL编码异常", e);
                return str;
            }
        }
    }

}
