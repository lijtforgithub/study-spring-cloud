package com.ljt.study.huafa.dto.permit.request;

import com.ljt.study.huafa.dto.permit.PermitBaseRequest;
import lombok.Data;

/**
 * @author LiJingTang
 * @date 2023-05-24 10:24
 */
@Data
public class UserProjectRequest extends PermitBaseRequest {

    private String userId;
    private String clientCode = "CUSTOMER_SERVICE";

}
