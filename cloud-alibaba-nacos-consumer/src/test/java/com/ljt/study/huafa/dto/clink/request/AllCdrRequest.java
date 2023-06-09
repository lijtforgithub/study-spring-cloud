package com.ljt.study.huafa.dto.clink.request;

import com.ljt.study.huafa.dto.clink.ClinkBaseRequest;
import lombok.Data;

/**
 * @author LiJingTang
 * @date 2023-06-06 11:47
 */
@Data
public class AllCdrRequest extends ClinkBaseRequest {

    /**
     * 座席号，要求只能是 4-11 位数字
     */
    private String cno;
    /**
     * 0：全部 1：呼入 2：外呼
     */
    private Integer type;

    private String businessLineAndSystem;

    private String requestUniqueId;

}
