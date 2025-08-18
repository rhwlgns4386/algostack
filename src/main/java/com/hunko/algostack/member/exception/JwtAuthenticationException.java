package com.hunko.algostack.member.exception;

import io.jsonwebtoken.ExpiredJwtException;

public class JwtAuthenticationException extends RuntimeException {
    public JwtAuthenticationException(Exception exception) {
        super(exception);
    }
}
