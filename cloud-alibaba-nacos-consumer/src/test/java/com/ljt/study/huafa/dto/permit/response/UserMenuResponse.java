package com.ljt.study.huafa.dto.permit.response;

import com.ljt.study.huafa.dto.permit.PermitBaseResponse;
import lombok.Data;

import java.util.List;

/**
 * @author LiJingTang
 * @date 2023-06-10 17:22
 */
@Data
public class UserMenuResponse extends PermitBaseResponse<List<UserMenuResponse.UserMenu>> {


    @Data
    public static class UserMenu {

        /**
         * 客户端编码
         */
        private String clientCode;

        /**
         * 图标
         */
        private String icon;

        /**
         * ID
         */
        private Long Id;

        /**
         * 层级
         */
        private String level;

        /**
         * 菜单ID
         */
        private String menuId;

        /**
         * 菜单名称
         */
        private String menuName;

        /**
         * 菜单类型
         */
        private String menuType;

        /**
         * 参数（菜单URL）
         */
        private String parameter;

        /**
         * 排序码
         */
        private Integer sort;

        /**
         * 上级节点ID
         */
        private String upNodeId;

        /**
         * 组件ID
         */
        private String wdaId;

        /**
         * 子菜单
         */
        private List<UserMenu> children;

    }

}
