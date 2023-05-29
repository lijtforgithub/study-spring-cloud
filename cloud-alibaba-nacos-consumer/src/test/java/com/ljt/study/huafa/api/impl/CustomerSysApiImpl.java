package com.ljt.study.huafa.api.impl;

import com.ljt.study.huafa.api.CustomerSysApi;
import com.ljt.study.huafa.dto.customer.request.OwnerInfoRequest;
import com.ljt.study.huafa.dto.customer.request.OwnerListRequest;
import com.ljt.study.huafa.dto.customer.response.OwnerInfoResponse;
import com.ljt.study.huafa.dto.customer.response.OwnerListResponse;
import com.ljt.study.huafa.client.CustomerHttpClient;
import lombok.RequiredArgsConstructor;

import static com.ljt.study.huafa.enums.CustomerRequestEnum.*;

/**
 * @author LiJingTang
 * @date 2023-05-21 10:09
 */
@RequiredArgsConstructor
public class CustomerSysApiImpl implements CustomerSysApi {

    private final CustomerHttpClient client;

    @Override
    public OwnerListResponse getOwnerList(OwnerListRequest request) {
        return client.execute(GET_INCREMENT_OWNER_INFO, request, OwnerListResponse.class);
    }

    @Override
    public OwnerInfoResponse getOwnerInfo(OwnerInfoRequest request) {
        return client.execute(GET_OWNER_INFO, request, OwnerInfoResponse.class);
    }

}
