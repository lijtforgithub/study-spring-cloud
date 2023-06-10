package com.ljt.study.huafa.dto.permit.response;

import com.ljt.study.huafa.dto.permit.PermitBaseResponse;
import lombok.Data;

import java.util.List;

/**
 * @author LiJingTang
 * @date 2023-06-10 17:22
 */
@Data
public class UserPermissionResponse extends PermitBaseResponse<List<UserPermissionResponse.UserPermission>> {


    @Data
    public static class UserPermission {

        /**
         * 客户端编码
         */
        private String fieldId;

        /**
         * 图标
         */
        private String fieldName;

        /**
         * ID
         */
        private String objectId;

        /**
         * 层级
         */
        private String objectName;

        /**
         * 组件ID
         */
        private String wdaId;

    }

}
