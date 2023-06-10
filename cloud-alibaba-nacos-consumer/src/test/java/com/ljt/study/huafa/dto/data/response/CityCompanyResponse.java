package com.ljt.study.huafa.dto.data.response;

import com.ljt.study.huafa.dto.data.DataBaseResponse;
import lombok.Data;

/**
 * @author LiJingTang
 * @date 2023-05-21 11:02
 */
@Data
public class CityCompanyResponse extends DataBaseResponse<CityCompanyResponse.CityCompany> {


    @Data
    public static class CityCompany extends CommonResponse {

        /**
         * 城市公司ID
         */
        private String cityCompanyId;

        /**
         * 城市公司名称
         */
        private String cityCompanyName;

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
