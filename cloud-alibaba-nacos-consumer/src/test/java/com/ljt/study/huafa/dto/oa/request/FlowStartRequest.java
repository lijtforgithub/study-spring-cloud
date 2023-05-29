package com.ljt.study.huafa.dto.oa.request;

import com.ljt.study.huafa.dto.oa.OABaseRequest;
import lombok.Data;

/**
 * @author LiJingTang
 * @date 2023-05-23 19:07
 */
@Data
public class FlowStartRequest extends OABaseRequest {

    /**
     * 来源系统名
     */
    private String systemName;
    /**
     *  OA登录名
     */
    private String loginName;
    /**
     * 验证串 约定密匙 ：如sap系统为  md5转的”sap+loginName
     */
    private String authCode;
    /**
     * 流程名
     */
    private String subject;
    /**
     * 流程编码
     */
    private String flowCode;
    /**
     * 流程表单数据
     */
    private String datas;
    /**
     * 流程附件
     */
    private String files;
    /**
     * 公司名
     */
    private String accountName;
    /**
     * 发送状态，0，流程直接发起  1，放入发起人待发事项
     */
    private String sendState;
    /**
     * 接口版本
     */
    private String version;

}
