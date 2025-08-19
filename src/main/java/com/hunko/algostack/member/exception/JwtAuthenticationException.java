package com.hunko.algostack.member.exception;

public class JwtAuthenticationException extends RuntimeException {
    public JwtAuthenticationException(Exception exception) {
        super(exception);
    }
}
