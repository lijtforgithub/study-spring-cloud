package com.ljt.study.rest;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.HttpRequestWrapper;
import org.springframework.util.StopWatch;
import org.springframework.util.StreamUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Objects;

import static com.ljt.study.rest.RestTemplateWrapper.ACCESS_TOKEN;
import static com.ljt.study.rest.RestTemplateWrapper.URL_TOKEN;

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
        log.info("请求： {} => {}", request.getURI(), new String(body));

        if (!isGetTokenUrl(request.getURI())) {
            UriComponentsBuilder builder = UriComponentsBuilder.fromUri(request.getURI());
            if (builder.build().getQueryParams().containsKey(ACCESS_TOKEN)) {
                log.info("{} 已设置Token", request.getURI());
            } else {
                RestTemplateWrapper.TokenDTO token = Objects.requireNonNull(restTemplateWrapper.getToken(), "获取Token失败");
                URI newUri = builder.queryParam(ACCESS_TOKEN, token.getAccessToken()).build().toUri();
                request = new ClientHttpRequestWrapper(newUri, request);
            }
        }
        stopWatch.stop();

        stopWatch.start("execute");
        ClientHttpResponse response = execution.execute(request, body);
        stopWatch.stop();

        stopWatch.start("post");
        response = new ClientHttpResponseWrapper(response);
        log.info("响应： {} => {}", response.getRawStatusCode(), new String(IOUtils.toByteArray(response.getBody())));
        stopWatch.stop();

        log.info(stopWatch.prettyPrint());
        return response;
    }

    private boolean isGetTokenUrl(URI uri) {
        return uri.getRawPath().endsWith(URL_TOKEN);
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

}
