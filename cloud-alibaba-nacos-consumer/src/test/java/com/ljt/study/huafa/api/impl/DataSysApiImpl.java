package com.ljt.study.huafa.api.impl;

import com.ljt.study.huafa.api.DataSysApi;
import com.ljt.study.huafa.dto.data.request.AreaRequest;
import com.ljt.study.huafa.dto.data.response.AreaResponse;
import com.ljt.study.huafa.client.DataHttpClient;
import lombok.RequiredArgsConstructor;

import static com.ljt.study.huafa.enums.DataRequestEnum.GET_AREA_LIST;

/**
 * @author LiJingTang
 * @date 2023-05-21 11:44
 */
@RequiredArgsConstructor
public class DataSysApiImpl implements DataSysApi {

    private final DataHttpClient client;

    @Override
    public AreaResponse getAreaList(AreaRequest request) {
        return client.execute(GET_AREA_LIST, request, AreaResponse.class);
    }

}