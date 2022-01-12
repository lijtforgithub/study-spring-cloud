package com.ljt.study.inteceptor;

import com.alibaba.fastjson.JSON;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Objects;

import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static javax.servlet.http.HttpServletResponse.SC_OK;

/**
 * @author jtli3
 * @date 2022-01-06 20:02
 */
@Slf4j
@WebFilter(urlPatterns = "/*")
public class RequestLogFilter implements Filter {

    @Resource
    private ThreadPoolTaskExecutor requestLogTaskExecutor;
    @Autowired
    private DefaultErrorAttributes defaultErrorAttributes;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.debug("请求日志Filter开始");
        RepeatableRequestWrapper requestWrapper = new RepeatableRequestWrapper((HttpServletRequest) request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper((HttpServletResponse) response);
        RequestLog reqLog = RequestLogHolder.get();

        try {
            reqLog.setPath(requestWrapper.getRequestURI())
                    .setReqParam(CollectionUtils.isEmpty(requestWrapper.getParameterMap()) ? null : JSON.toJSONString(requestWrapper.getParameterMap()))
                    .setReqBody(new String(IOUtils.toByteArray(requestWrapper.getInputStream())));

            chain.doFilter(requestWrapper, responseWrapper);
        } catch (ServletException e) {
            reqLog.setStatusCode(SC_INTERNAL_SERVER_ERROR)
                    .setErrorMsg(StringUtils.left(e.getMessage(), 200));
            throw e;
        } finally {
            reqLog.setResp(new String(responseWrapper.getContentAsByteArray()))
                    .setEndDateTime(new Date());
            responseWrapper.copyBodyToResponse();

            if (Objects.isNull(reqLog.getStatusCode())) {
                reqLog.setStatusCode(responseWrapper.getStatusCode());
            }
            if (SC_OK != reqLog.getStatusCode() && StringUtils.isBlank(reqLog.getErrorMsg())) {
                Throwable e = defaultErrorAttributes.getError(new ServletWebRequest(requestWrapper));
                if (Objects.nonNull(e)) {
                    reqLog.setErrorMsg(StringUtils.left(e.getMessage(), 200));
                }
            }

            requestLogTaskExecutor.execute(new RequestLogTask(reqLog));
            RequestLogHolder.remove();
        }

        log.debug("请求日志Filter结束");
    }


    private static class RepeatableRequestWrapper extends HttpServletRequestWrapper {

        private final byte[] body;
        private final Charset charset;

        @SneakyThrows
        public RepeatableRequestWrapper(HttpServletRequest request, Charset charset) {
            super(request);
            body = IOUtils.toByteArray(request.getInputStream());
            this.charset = charset;
        }

        public RepeatableRequestWrapper(HttpServletRequest request) {
            this(request, StandardCharsets.UTF_8);
        }

        @Override
        public ServletInputStream getInputStream() {
            return new RepeatableInputStream();
        }

        @Override
        public BufferedReader getReader() {
            return new BufferedReader(new InputStreamReader(getInputStream(), charset));
        }


        private class RepeatableInputStream extends ServletInputStream {

            private final ByteArrayInputStream byteArrayInputStream;

            @Override
            public synchronized void reset() {
                byteArrayInputStream.reset();
            }

            @Override
            public synchronized void mark(int readLimit) {
                byteArrayInputStream.mark(readLimit);
            }

            public RepeatableInputStream() {
                byteArrayInputStream = new ByteArrayInputStream(body);
            }

            @Override
            public boolean isFinished() {
                return byteArrayInputStream.available() == 0;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
                throw new UnsupportedOperationException("不支持监听");
            }

            @Override
            public int read() {
                return byteArrayInputStream.read();
            }

            @Override
            public boolean markSupported() {
                return byteArrayInputStream.markSupported();
            }
        }

    }

}
