package com.ljt.study.huafa.api;

import com.ljt.study.huafa.dto.customer.request.DictRequest;
import com.ljt.study.huafa.dto.customer.request.OwnerIncrRequest;
import com.ljt.study.huafa.dto.customer.response.DictResponse;
import com.ljt.study.huafa.dto.customer.response.OwnerIncrResponse;

/**
 * @author LiJingTang
 * @date 2023-05-20 23:08
 */
public interface CustomerSysApi {

    /**
     * 查询增量业主
     *
     * {"address":"",
     * "certNumber":"413027197212231435",
     * "certTypeId":"1023001",
     * "certTypeName":"身份证",
     * "customerType":10,
     * "deleted":0,
     * "gender":2,
     * "mobile":"13326619578",
     * "ownerId":"002fa5fb9db04968bdf4cc075cb4c6a8",
     * "ownerName":"吴彰森"}
     */
    OwnerIncrResponse listIncrOwner(OwnerIncrRequest request);

    /**
     * 查询增量业主房客关系
     *
     * {"address":"无",
     * "certNumber":"210281198111218825",
     * "certTypeId":"1023001",
     * "certTypeName":"身份证",
     * "customerType":10,
     * "dealStatus":10,
     * "deleted":0,
     * "gender":1,
     * "handoverDate":1650772926000,
     * "houseId":"1029A1100000000084J2",
     * "isSecond":0,
     * "mobile":"15041167307",
     * "ownerHouseRelId":"91",
     * "ownerId":"c6eb57b058a84680a965014e594bb7fe",
     * "ownerName":"刘艳华",
     * "ownerRel":0}
     */
    OwnerIncrResponse listIncrOwnerRoom(OwnerIncrRequest request);

    DictResponse getDict(DictRequest request);

}
