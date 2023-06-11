package com.ljt.study.huafa.dto.oa.response;

import com.ljt.study.huafa.dto.oa.OABaseResponse;
import lombok.Data;

/**
 * @author LiJingTang
 * @date 2023-05-23 19:08
 */
@Data
public class StartFlowResponse extends OABaseResponse {

    /**
     * 状态码
     * 1，发起成功
     * 2，参数有误
     * 3，附件上传异常
     * 4，未找到对应流程
     * 5，datas数据解析失败
     * 6，流程发起异常
     */
    private String state;

    /**
     * 是否成功 0不成功  1成功
     */
    private String isSuccess;

    /**
     * 具体原因说明
     */
    private String msg;

    /**
     * 流程ID
     */
    private String flowId;

}
