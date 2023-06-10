package com.ljt.study.huafa.dto.clink.response;

import com.ljt.study.huafa.dto.clink.ClinkBaseResponse;
import lombok.Data;

/**
 * @author LiJingTang
 * @date 2023-06-10 10:40
 */
@Data
public class CallRecordUrlResponse extends ClinkBaseResponse {

    private String recordFileUrl;

}
