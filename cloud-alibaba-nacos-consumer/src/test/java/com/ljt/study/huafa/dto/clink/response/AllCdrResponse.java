package com.ljt.study.huafa.dto.clink.response;

import com.ljt.study.huafa.dto.clink.ClinkBaseResponse;
import lombok.Data;

import java.util.List;

/**
 * @author LiJingTang
 * @date 2023-06-06 11:52
 */
@Data
public class AllCdrResponse extends ClinkBaseResponse {

    private Integer pageNumber;
    private Integer pageSize;
    private Integer totalCount;
    private List<Cdr> cdrs;


    @Data
    public static class Cdr {

        private String uniqueId;
        private String hotline;
    }

}
