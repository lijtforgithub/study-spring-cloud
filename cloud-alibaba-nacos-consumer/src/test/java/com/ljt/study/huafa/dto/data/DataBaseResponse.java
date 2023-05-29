package com.ljt.study.huafa.dto.data;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * @author LiJingTang
 * @date 2023-05-21 10:45
 */
@Data
public abstract class DataBaseResponse<T> {

    @JSONField(name = "resultcode")
    private int resultCode;
    @JSONField(name = "resultmsg")
    private String resultMsg;

    private int count;
    private int total;

    private List<T> records;

}
