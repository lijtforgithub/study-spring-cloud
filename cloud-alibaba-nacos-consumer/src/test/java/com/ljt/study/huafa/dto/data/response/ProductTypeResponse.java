package com.ljt.study.huafa.dto.data.response;

import com.ljt.study.huafa.dto.data.DataBaseResponse;
import lombok.Data;

/**
 * @author LiJingTang
 * @date 2023-05-21 11:02
 */
@Data
public class ProductTypeResponse extends DataBaseResponse<ProductTypeResponse.ProductType> {


    @Data
    public static class ProductType extends CommonResponse {

        /**
         * 产品类型ID
         */
        private String productTypeId;

        /**
         * 产品类型名称
         */
        private String productTypeName;

        /**
         * 级别
         */
        private Integer level;

        /**
         * 父产品类型编码
         */
        private String parentProductTypeId;

        /**
         * 状态
         */
        private Integer status;

    }

}
