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

    private String path;
    private String reqUrl;
    private String reqBody;
    private String resp;

    private String aiPath;
    private String aiReqUrl;
    private String aiReqBody;
    private String aiResp;
    private Long aiCostTime;

    private Date startDateTime;
    private Date endDateTime;
    private Date createDateTime;

}
