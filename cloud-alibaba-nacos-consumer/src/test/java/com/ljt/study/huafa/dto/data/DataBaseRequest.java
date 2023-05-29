package com.ljt.study.huafa.dto.data;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author LiJingTang
 * @date 2023-05-21 10:44
 */
@Data
public class DataBaseRequest {

    private final String format = "json";

    private LocalDate updateDate;

    @JSONField(name = "@pageNo")
    private Integer pageNo;
    @JSONField(name = "@pageSize")
    private Integer pageSize;


}
