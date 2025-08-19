package com.hunko.algostack.shared.common.exception;

import org.springframework.http.HttpStatus;

public interface ExceptionType {

    HttpStatus getHttpStatus();

    String getMessage();

    default GlobalException toException() {
        return new GlobalException(this);
    }

    default GlobalException toException(Throwable cause) {
        return new GlobalException(cause, this);
    }

    default GlobalException toException(Throwable cause, String logMessage) {
        return new GlobalException(cause, this, logMessage);
    }

    default void throwException() {
        throw toException();
    }

    default void throwException(Throwable cause) {
        throw  toException(cause);
    }

    default void throwException(Throwable cause, String logMessage) {
        throw toException(cause, logMessage);
    }
}
