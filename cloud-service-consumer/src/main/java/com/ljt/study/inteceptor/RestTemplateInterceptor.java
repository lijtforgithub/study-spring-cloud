package com.ljt.study.inteceptor;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.HttpRequestWrapper;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StopWatch;
import org.springframework.util.StreamUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.ljt.study.inteceptor.ApiPathEnum.GET_TOKEN;

/**
 * @author LiJingTang
 * @date 2021-12-31 14:10
 */
@Slf4j
class RestTemplateInterceptor implements ClientHttpRequestInterceptor {

    private final RestTemplateWrapper restTemplateWrapper;

    public RestTemplateInterceptor(RestTemplateWrapper restTemplateWrapper) {
        this.restTemplateWrapper = restTemplateWrapper;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("pre");
//        if (!isGetTokenUrl(request.getURI())) {
//            UriComponentsBuilder builder = UriComponentsBuilder.fromUri(request.getURI());
//            MultiValueMap<String, String> map = builder.build().getQueryParams();
//            if (!map.containsKey(APP_KEY)) {
//                builder.queryParam(APP_KEY, restTemplateWrapper.getClientSecret());
//            }
//            if (!map.containsKey(DEVICE_ID)) {
//                builder.queryParam(DEVICE_ID, restTemplateWrapper.getClientId());
//            }
//            if (!map.containsKey(ACCESS_TOKEN)) {
//                RestTemplateWrapper.TokenDTO token = restTemplateWrapper.getToken();
//                builder.queryParam(ACCESS_TOKEN, token.getAccessToken());
//            }
//            request = new ClientHttpRequestWrapper(builder.build().toUri(), request);
//            log.debug("新请求：{}", request.getURI());
//        }

        RequestLog reqLog = RequestLogHolder.get();
        URI uri = request.getURI();
        reqLog.setAiPath(uri.getPath())
                .setAiReqParam(getReqParam(uri))
                .setAiReqBody(new String(body));
        stopWatch.stop();

        stopWatch.start("execute");
        ClientHttpResponse response = execution.execute(request, body);
        stopWatch.stop();

        stopWatch.start("post");
        response = new ClientHttpResponseWrapper(response);
        reqLog.setAiStatusCode(response.getRawStatusCode())
                .setErrorMsg(response.getStatusText())
                .setAiCostMillis(stopWatch.getLastTaskTimeMillis())
                .setAiResp(new String(IOUtils.toByteArray(response.getBody())));
        stopWatch.stop();

        log.info(stopWatch.prettyPrint());
        return response;
    }

    private String getReqParam(URI uri) {
        MultiValueMap<String, String> params = UriComponentsBuilder.fromUri(uri).build().getQueryParams();
        if (CollectionUtils.isEmpty(params)) {
            return null;
        }
        Map<String, Object> map = Maps.newHashMapWithExpectedSize(params.size());
        params.forEach((k, v) -> {
            List<String> list = v.stream().map(s -> {
                try {
                    return URLDecoder.decode(s, StandardCharsets.UTF_8.name());
                } catch (UnsupportedEncodingException e) {
                    return null;
                }
            }).collect(Collectors.toList());
            map.put(k, list.size() == 1 ? list.get(0) : list);
        });
        return JSON.toJSONString(map);
    }

    private boolean isGetTokenUrl(URI uri) {
        return uri.getRawPath().endsWith(GET_TOKEN.getPath());
    }


    private static class ClientHttpRequestWrapper extends HttpRequestWrapper {
        private final URI uri;

        ClientHttpRequestWrapper(URI uri, HttpRequest request) {
            super(request);
            this.uri = uri;
        }

        @Override
        public URI getURI() {
            return this.uri;
        }

    }


    private static class ClientHttpResponseWrapper implements ClientHttpResponse {

        private final ClientHttpResponse response;
        private byte[] body;

        private ClientHttpResponseWrapper(ClientHttpResponse response) {
            this.response = response;
        }

        @Override
        public HttpStatus getStatusCode() throws IOException {
            return this.response.getStatusCode();
        }

        @Override
        public int getRawStatusCode() throws IOException {
            return this.response.getRawStatusCode();
        }

        @Override
        public String getStatusText() throws IOException {
            return this.response.getStatusText();
        }

        @Override
        public HttpHeaders getHeaders() {
            return this.response.getHeaders();
        }

        @Override
        public InputStream getBody() throws IOException {
            if (this.body == null) {
                this.body = StreamUtils.copyToByteArray(this.response.getBody());
            }
            return new ByteArrayInputStream(this.body);
        }

        @Override
        public void close() {
            this.response.close();
        }
    }

    private static class ParamClientHttpResponse implements ClientHttpResponse {

        @Override
        public HttpStatus getStatusCode() throws IOException {
            return HttpStatus.OK;
        }

        @Override
        public int getRawStatusCode() throws IOException {
            return HttpStatus.OK.value();
        }

        @Override
        public String getStatusText() throws IOException {
            return "设备ID或者授权码为空";
        }

        @Override
        public void close() {

        }

        @Override
        public InputStream getBody() throws IOException {
            ImmutableMap<String, String> map = ImmutableMap.of("code", "400", "message", getStatusText());
            return new ByteArrayInputStream(JSON.toJSONString(map).getBytes());
        }

        @Override
        public HttpHeaders getHeaders() {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            return headers;
        }

    }

}
