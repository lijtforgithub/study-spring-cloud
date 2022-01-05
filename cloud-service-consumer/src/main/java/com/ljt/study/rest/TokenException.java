package com.ljt.study.rest;

/**
 * @author LiJingTang
 * @date 2022-01-04 13:53
 */
public class TokenException extends RuntimeException {

    private static final long serialVersionUID = -7079169739856525023L;

    public TokenException(String message) {
        super(message);
    }
}
