package com.ljt.study.inteceptor;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author jtli3
 * @date 2022-01-07 14:56
 */
@Slf4j
@Getter
public class RequestLogTask implements Runnable {

    private final RequestLog requestLog;

    public RequestLogTask(RequestLog requestLog) {
        this.requestLog = requestLog;
    }

    @Override
    public void run() {
        log.info("保存请求日志：{}", JSON.toJSONString(requestLog));
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
