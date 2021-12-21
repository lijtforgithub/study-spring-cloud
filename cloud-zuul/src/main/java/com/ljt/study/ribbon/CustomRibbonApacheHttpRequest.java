package com.ljt.study.ribbon;

import com.ljt.study.properties.RibbonTimeoutProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.springframework.cloud.netflix.ribbon.apache.RibbonApacheHttpRequest;
import org.springframework.cloud.netflix.ribbon.support.RibbonCommandContext;
import org.springframework.util.PathMatcher;

import static com.ljt.study.ribbon.CustomHttpClientRibbonCommand.isConfig;

/**
 * @author LiJingTang
 * @date 2021-12-21 11:00
 */
@Slf4j
public class CustomRibbonApacheHttpRequest extends RibbonApacheHttpRequest {

    private final RibbonTimeoutProperties ribbonTimeoutProperties;
    private final PathMatcher pathMatcher;

    public CustomRibbonApacheHttpRequest(RibbonCommandContext context, RibbonTimeoutProperties ribbonTimeoutProperties, PathMatcher pathMatcher) {
        super(context);
        this.ribbonTimeoutProperties = ribbonTimeoutProperties;
        this.pathMatcher = pathMatcher;
    }

    @Override
    public HttpUriRequest toRequest(RequestConfig requestConfig) {
        HttpUriRequest request = super.toRequest(requestConfig);

        // 满足配置 重新设置请求的超时时间
        if (request instanceof HttpRequestBase && isConfig(context, ribbonTimeoutProperties, pathMatcher)) {
            log.info("更新指定接口ribbon超时时间：{} = {}", context.getServiceId(), uri.getRawPath());
            HttpRequestBase requestBase = (HttpRequestBase) request;
            RequestConfig config = requestBase.getConfig();
            RequestConfig.Builder builder = RequestConfig.copy(config);
            if (ribbonTimeoutProperties.getConnectTimeout() > 0) {
                builder.setConnectTimeout(ribbonTimeoutProperties.getConnectTimeout());
            }
            if (ribbonTimeoutProperties.getSocketTimeout() > 0) {
                builder.setSocketTimeout(ribbonTimeoutProperties.getSocketTimeout());
            }
            requestBase.setConfig(builder.build());
        }

        return request;
    }

}
