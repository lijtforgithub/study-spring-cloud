package com.ljt.study.huafa.api.impl;

import com.ljt.study.huafa.api.CustomerSysApi;
import com.ljt.study.huafa.client.CustomerHttpClient;
import com.ljt.study.huafa.dto.customer.request.DictRequest;
import com.ljt.study.huafa.dto.customer.request.OwnerIncrRequest;
import com.ljt.study.huafa.dto.customer.response.DictResponse;
import com.ljt.study.huafa.dto.customer.response.OwnerIncrResponse;
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
    public OwnerIncrResponse listIncrOwner(OwnerIncrRequest request) {
        return client.execute(LIST_INCR_OWNER_INFO, request, OwnerIncrResponse.class);
    }

    @Override
    public OwnerIncrResponse listIncrOwnerRoom(OwnerIncrRequest request) {
        return client.execute(LIST_INCR_OWNER_ROOM, request, OwnerIncrResponse.class);
    }

    @Override
    public DictResponse getDict(DictRequest request) {
        return client.execute(GET_DICT_CONFIG, request, DictResponse.class);
    }

}
