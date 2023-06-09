package com.ljt.study.huafa.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author LiJingTang
 * @date 2023-06-07 10:39
 */
@Getter
public class ClientException extends RuntimeException {

    private final Integer code;
    private final String message;

    public ClientException(String message) {
        super(message);
        this.code = HttpStatus.INTERNAL_SERVER_ERROR.value();
        this.message = message;
    }

    public ClientException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

}
