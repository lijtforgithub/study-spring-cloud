package com.ljt.study.huafa.api;

import com.ljt.study.huafa.dto.clink.request.*;
import com.ljt.study.huafa.dto.clink.response.CallRecordResponse;
import com.ljt.study.huafa.dto.clink.response.ClientDetailResponse;
import com.ljt.study.huafa.dto.clink.response.ClinkLoginResponse;

/**
 * @author LiJingTang
 * @date 2023-06-07 09:10
 */
public interface ClinkSysApi {

    /**
     * 获取登录信息
     */
    ClinkLoginResponse getLoginInfo(ClinkLoginRequest request);

    /**
     * 查看座席详情
     */
    ClientDetailResponse getClientDetail(ClientDetailRequest request);

    /**
     * 查询通话记录列表
     */
    CallRecordResponse listCallRecord(CallRecordRequest request);

    /**
     * 查看通话录音地址
     */
    String getCallRecordUrl(CallRecordUrlRequest request);

    /**
     * 上线
     */
    void onLine(OnLineRequest request);

}
