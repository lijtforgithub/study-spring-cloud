package com.ljt.study.urlcustom;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.http.HttpServletRequestWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.util.PathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.*;

/**
 * 替换配置的zuul-servlet的URL为了成功路由
 *
 * @author LiJingTang
 * @date 2021-12-31 9:25
 */
@Slf4j
public class PreReplaceUrlFilter extends ZuulFilter {

    private static final PathMatcher PATH_MATCHER = new AntPathMatcher();

    @Autowired
    private ZuulProperties zuulProperties;
    @Autowired
    private UrlCustomProperties urlCustomProperties;

    @Override
    public int filterOrder() {
        return PRE_DECORATION_FILTER_ORDER - 1;
    }

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        String url = ctx.getRequest().getRequestURI();
        if (!url.startsWith(zuulProperties.getServletPath())) {
            return false;
        }

        Set<String> set = urlCustomProperties.getZuulServletUrl();
        if (CollectionUtils.isEmpty(set)) {
            return false;
        }

        String newUrl = url.substring(zuulProperties.getServletPath().length());
        return set.contains(newUrl) || set.stream().anyMatch(s -> PATH_MATCHER.match(s, newUrl));
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.put(IS_DISPATCHER_SERVLET_REQUEST_KEY, Boolean.TRUE);
        HttpServletRequest request = ctx.getRequest();
        String originUrl = request.getRequestURI();
        String newUrl = originUrl.replaceFirst(zuulProperties.getServletPath(), zuulProperties.getPrefix());
        log.info("替换URL：{} => {}", originUrl, newUrl);
        ctx.setRequest(new ReplaceUrlHttpRequestWrapper(request, newUrl));
        return null;
    }


    private static class ReplaceUrlHttpRequestWrapper extends HttpServletRequestWrapper {

        private final String url;

        ReplaceUrlHttpRequestWrapper(HttpServletRequest request, String url) {
            super(request);
            this.url = url;
        }

        @Override
        public String getRequestURI() {
            return url;
        }

    }

}
