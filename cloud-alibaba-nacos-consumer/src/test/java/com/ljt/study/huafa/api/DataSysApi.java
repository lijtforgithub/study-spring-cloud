package com.ljt.study.huafa.api;

import com.ljt.study.huafa.dto.data.request.AreaRequest;
import com.ljt.study.huafa.dto.data.response.AreaResponse;

/**
 * @author LiJingTang
 * @date 2023-05-21 11:42
 */
public interface DataSysApi {

    AreaResponse listArea(AreaRequest request);

}
