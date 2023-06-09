package com.ljt.study.huafa.api.impl;

import com.ljt.study.huafa.api.ClinkSysApi;
import com.ljt.study.huafa.client.ClinkHttpClient;
import com.ljt.study.huafa.dto.clink.request.AllCdrRequest;
import com.ljt.study.huafa.dto.clink.response.AllCdrResponse;
import lombok.RequiredArgsConstructor;

import static com.ljt.study.huafa.enums.ClinkRequestEnum.GET_ALL_CDR;

/**
 * @author LiJingTang
 * @date 2023-06-07 09:11
 */
@RequiredArgsConstructor
public class ClinkSysApiImpl implements ClinkSysApi {

    private final ClinkHttpClient client;

    @Override
    public AllCdrResponse getAllCdr(AllCdrRequest request) {
        return client.execute(GET_ALL_CDR, request, AllCdrResponse.class);
    }

}
