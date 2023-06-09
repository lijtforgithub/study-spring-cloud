package com.ljt.study.huafa.api;

import com.ljt.study.huafa.dto.clink.request.AllCdrRequest;
import com.ljt.study.huafa.dto.clink.response.AllCdrResponse;

/**
 * @author LiJingTang
 * @date 2023-06-07 09:10
 */
public interface ClinkSysApi {

    AllCdrResponse getAllCdr(AllCdrRequest request);

}
