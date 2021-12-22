package com.ljt.study.timeout;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.springframework.cloud.netflix.ribbon.apache.RibbonApacheHttpRequest;
import org.springframework.cloud.netflix.ribbon.support.RibbonCommandContext;

import static com.ljt.study.timeout.CustomHttpClientRibbonCommand.isMatch;

/**
 * 扩展Ribbon请求 设置单独接口的Ribbon超时时间
 *
 * @author LiJingTang
 * @date 2021-12-21 11:00
 */
@Slf4j
class CustomRibbonApacheHttpRequest extends RibbonApacheHttpRequest {

    private final RibbonTimeoutProperties ribbonTimeoutProperties;

    public CustomRibbonApacheHttpRequest(RibbonCommandContext context, RibbonTimeoutProperties ribbonTimeoutProperties) {
        super(context);
        this.ribbonTimeoutProperties = ribbonTimeoutProperties;
    }

    @Override
    public HttpUriRequest toRequest(RequestConfig requestConfig) {
        HttpUriRequest request = super.toRequest(requestConfig);

        // 满足配置 重新设置请求的超时时间
        if (isMatch(context, ribbonTimeoutProperties) && request instanceof HttpRequestBase) {
            HttpRequestBase requestBase = (HttpRequestBase) request;
            RequestConfig.Builder builder = RequestConfig.copy(requestConfig);

            if (ribbonTimeoutProperties.getConnectTimeout() > 0) {
                builder.setConnectTimeout(ribbonTimeoutProperties.getConnectTimeout());
            }
            if (ribbonTimeoutProperties.getSocketTimeout() > 0) {
                builder.setSocketTimeout(ribbonTimeoutProperties.getSocketTimeout());
            }

            RequestConfig config = builder.build();
            requestBase.setConfig(config);
            log.info("ribbon: 更新[{}]{}超时时间 ConnectTimeout = {}, SocketTimeout = {}", context.getServiceId(), uri.getRawPath(), config.getConnectTimeout(), config.getSocketTimeout());
        }

        return request;
    }

}
