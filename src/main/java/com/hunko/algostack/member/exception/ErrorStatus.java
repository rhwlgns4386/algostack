package com.hunko.algostack.member.exception;

import com.hunko.algostack.shared.common.exception.ExceptionType;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorStatus implements ExceptionType{

    LOGIN_FAIL(HttpStatus.BAD_REQUEST,"로그인에 실패 했습니다."),
    SING_IN_FAIL(HttpStatus.BAD_REQUEST,"회원가입에 실패 하였습니다."),
    AUTHENTICATION_FAIL(HttpStatus.UNAUTHORIZED,"인증에 실패하였습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    ErrorStatus(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
