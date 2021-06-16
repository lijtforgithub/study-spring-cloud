package com.ljt.study.filter;

import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Stream;

/**
 * @author LiJingTang
 * @date 2021-06-02 20:18
 */
public class FilterUtils {

    private FilterUtils() {
    }

    static final String AUTH_KEY = "is_auth";

    private static final String UN_KNOW = "unKnown";
    private static final String[] IP_HEADERS = {"X-Real-IP", "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR"};

    static void write(HttpServletResponse response, String content) throws IOException {
        response.setContentType("text/json; charset=utf-8");
        response.getWriter().write(content);
    }


    public static String getUserIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (isValid(ip)) {
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            return Stream.of(ip.split(",")).filter(item -> !UN_KNOW.equalsIgnoreCase(item)).findFirst().orElse(ip);
        }

        for (String header : IP_HEADERS) {
            ip = request.getHeader(header);
            if (isValid(ip)) {
                return ip;
            }
        }

        return request.getRemoteAddr();
    }

    private static boolean isValid(String ip) {
        return StringUtils.isNotBlank(ip) && !UN_KNOW.equalsIgnoreCase(ip);
    }

}
