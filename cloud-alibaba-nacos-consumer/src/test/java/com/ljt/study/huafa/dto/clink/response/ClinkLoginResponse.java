package com.ljt.study.huafa.dto.clink.response;

import lombok.Data;

/**
 * @author LiJingTang
 * @date 2023-06-09 15:50
 */
@Data
public class ClinkLoginResponse {

    /**
     * 是否获取成功
     */
    private Boolean isLogin;

    /**
     * 平台编号(业务系统)
     */
    private String platform;

    /**
     * 绑定电话
     */
    private String bindTel;

    /**
     * 绑定类型，1：普通电话、2：IP话机、3：软电话
     */
    private Integer bindType;

    /**
     * 业务线标识
     */
    private String businessLine;

    /**
     * 座席号
     */
    private String cno;

    /**
     * 加密信息
     */
    private String token;

}
