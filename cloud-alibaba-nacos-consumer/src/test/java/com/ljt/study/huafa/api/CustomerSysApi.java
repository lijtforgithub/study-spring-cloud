package com.ljt.study.huafa.api;

import com.ljt.study.huafa.dto.customer.request.OwnerInfoRequest;
import com.ljt.study.huafa.dto.customer.request.OwnerListRequest;
import com.ljt.study.huafa.dto.customer.response.OwnerInfoResponse;
import com.ljt.study.huafa.dto.customer.response.OwnerListResponse;

/**
 * @author LiJingTang
 * @date 2023-05-20 23:08
 */
public interface CustomerSysApi {

    OwnerListResponse getOwnerList(OwnerListRequest request);

    OwnerInfoResponse getOwnerInfo(OwnerInfoRequest request);

}
