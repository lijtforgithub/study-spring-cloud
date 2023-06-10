package com.ljt.study.huafa.dto.data.response;

import com.ljt.study.huafa.dto.data.DataBaseResponse;
import lombok.Data;

/**
 * @author LiJingTang
 * @date 2023-05-21 11:02
 */
@Data
public class AreaCompanyResponse extends DataBaseResponse<AreaCompanyResponse.AreaCompany> {

    @Data
    public static class AreaCompany extends CommonResponse {
        /**
         * 	区域公司ID
         */
        private String areaCompanyId;

        /**
         * 	区域公司名称
         */
        private String areaCompanyName;

    }

}
