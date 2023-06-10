package com.ljt.study.huafa.dto.clink.request;

import com.ljt.study.huafa.dto.clink.ClinkBaseRequest;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author LiJingTang
 * @date 2023-06-10 10:38
 */
@Data
public class CallRecordUrlRequest extends ClinkBaseRequest {

    /**
     * 通话记录唯一标识
     */
    @NotBlank(message = "通话记录唯一标识不能为空")
    private String mainUniqueId;

    /**
     * 不传递获取mp3格式录音，传递时获取wav格式录音。1：双轨录音客户侧，2：双轨录音座席侧，3：两侧合成录音
     */
    private Integer recordSide;

    /**
     * 获取录音地址超时时长，单位为秒，默认为一小时，范围在一到二十四小时。
     */
    private Long timeout;

}
