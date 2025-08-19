package com.hunko.algostack.shared.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class GlobalException extends RuntimeException {
    private final ExceptionType exceptionType;
    private String logMessage;

    GlobalException(Throwable cause, ExceptionType exceptionType, String logMessage) {
        super(exceptionType.getMessage(), cause);
        this.exceptionType = exceptionType;
        this.logMessage = logMessage;
    }

    GlobalException(Throwable cause, ExceptionType exceptionType) {
        super(exceptionType.getMessage(), cause);
        this.exceptionType = exceptionType;
    }

    public boolean hasLogMessage() {
        return this.logMessage != null;
    }
}
