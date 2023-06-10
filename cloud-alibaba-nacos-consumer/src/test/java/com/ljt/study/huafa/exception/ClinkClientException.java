package com.ljt.study.huafa.exception;

import com.ljt.study.huafa.dto.clink.ResponseError;
import lombok.Getter;

/**
 * @author LiJingTang
 * @date 2023-06-10 11:34
 */
@Getter
public class ClinkClientException extends ClientException {

    private final ResponseError error;

    public ClinkClientException(String message, ResponseError error) {
        super(message);
        this.error = error;
    }

    public ClinkClientException(Integer code, String message, ResponseError error) {
        super(code, message);
        this.error = error;
    }

}
