package com.ljt.study.cloud.filter;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author LiJingTang
 * @date 2021-06-02 20:18
 */
public class FilterUtils {

    private FilterUtils() {}

    static final String AUTH_KEY = "is_auth";

    static void write(HttpServletResponse response, String content) throws IOException {
        response.setContentType("text/json; charset=utf-8");
        response.getWriter().write(content);
    }

}
