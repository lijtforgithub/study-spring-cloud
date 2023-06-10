package com.ljt.study.huafa.dto.permit.response;

import com.ljt.study.huafa.dto.permit.PermitBaseResponse;
import lombok.Data;

import java.util.List;

/**
 * @author LiJingTang
 * @date 2023-05-22 15:54
 */
@Data
public class UserPositionResponse extends PermitBaseResponse<List<UserPositionResponse>> {

    /**
     * 岗位ID
     */
    private String positionId;

    /**
     * 岗位名称
     */
    private String positionName;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户名称
     */
    private String userName;

}
