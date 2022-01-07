package com.ljt.study.inteceptor;

import java.util.Date;

/**
 * @author jtli3
 * @date 2022-01-06 18:22
 */
public class RequestLogHolder {

    public static final ThreadLocal<RequestLog> REQUEST_LOG = new InheritableThreadLocal<RequestLog>() {
        @Override
        protected RequestLog initialValue() {
            RequestLog log = new RequestLog();
            log.setStartDateTime(new Date());
            return log;
        }
    };

    public static RequestLog get() {
        return REQUEST_LOG.get();
    }

    public static void remove() {
        REQUEST_LOG.remove();
    }

}
