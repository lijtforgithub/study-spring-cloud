package com.ljt.study.fallback;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.ImmutableMap;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * @author LiJingTang
 * @date 2021-06-07 10:05
 */
public class CustomFallback implements FallbackProvider {

    @Override
    public String getRoute() {
        return "*";
    }

    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        return new ClientHttpResponse() {

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                return headers;
            }

            @Override
            public InputStream getBody() {
                ImmutableMap<String, String> map = ImmutableMap.of("route", route, "msg", cause.getMessage());
                return new ByteArrayInputStream(JSON.toJSONString(map).getBytes());
            }

            @Override
            public HttpStatus getStatusCode() {
                return HttpStatus.BAD_REQUEST;
            }

            @Override
            public int getRawStatusCode() {
                return HttpStatus.BAD_REQUEST.value();
            }

            @Override
            public String getStatusText() {
                return cause.getLocalizedMessage();
            }

            @Override
            public void close() {

            }
        };
    }

}
