package com.ljt.study.huafa.api;

import com.ljt.study.huafa.dto.oa.request.StartFlowRequest;
import com.ljt.study.huafa.dto.oa.request.StartFlowSimpleRequest;
import com.ljt.study.huafa.dto.oa.response.FlowDetailResponse;
import com.ljt.study.huafa.dto.oa.response.FlowStatusResponse;
import com.ljt.study.huafa.dto.oa.response.StartFlowResponse;

/**
 * @author LiJingTang
 * @date 2023-05-24 17:25
 */
public interface OASysApi {

    /**
     * 启动流程-简化参数
     */
    StartFlowResponse startFlow(StartFlowSimpleRequest request);

    /**
     * 启动流程
     */
    StartFlowResponse startFlow(StartFlowRequest request);

    /**
     * 获取流程表单详情
     *
     * @param flowId 流程ID
     */
    FlowDetailResponse getFlowDetail(String flowId);

    /**
     * 获取流程表单详情
     *
     * @param flowId 流程ID
     */
    FlowStatusResponse getFlowStatus(String flowId);

    /**
     * 生成验证串
     */
    String generateAuthCode(String systemName, String loginName);

}
