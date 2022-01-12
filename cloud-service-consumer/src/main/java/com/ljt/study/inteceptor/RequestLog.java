package com.ljt.study.inteceptor;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author jtli3
 * @date 2022-01-06 18:18
 */
@Data
@Accessors(chain = true)
public class RequestLog implements Serializable {

    private static final long serialVersionUID = 4183405379932755181L;

    private Long id;

    /**
     * 请求路径
     */
    private String path;
    /**
     * 请求参数
     */
    private String reqParam;
    /**
     * 请求体
     */
    private String reqBody;
    /**
     * 响应数据
     */
    private String resp;
    /**
     * 响应状态码
     */
    private Integer statusCode;
    /**
     * 异常信息
     */
    private String errorMsg;

    /**
     * 辅诊平台请求路径
     */
    private String aiPath;
    /**
     * 辅诊平台请求参数
     */
    private String aiReqParam;
    /**
     * 辅诊平台请求体
     */
    private String aiReqBody;
    /**
     * 辅诊平台响应数据
     */
    private String aiResp;
    /**
     * 辅诊平台耗时微秒
     */
    private Long aiCostMillis;
    /**
     * 辅诊平台响应状态码
     */
    private Integer aiStatusCode;
    /**
     * 辅诊平台异常信息
     */
    private String aiErrorMsg;

    /**
     * 请求开始时间
     */
    private Date startDateTime;
    /**
     * 请求结束时间
     */
    private Date endDateTime;
    /**
     * 落库时间
     */
    private Date createDateTime;

}
