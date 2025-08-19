package com.hunko.algostack.shared.common.exception.handler;

import com.hunko.algostack.shared.common.exception.ExceptionType;
import com.hunko.algostack.shared.common.exception.GlobalException;
import com.hunko.algostack.shared.common.exception.mapper.ErrorResponseMapper;
import com.hunko.algostack.shared.common.exception.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<ErrorResponse> exception(GlobalException exception) {
        writeErrorLog(exception);
        return toResponse(exception);
    }

    private void writeErrorLog(GlobalException exception) {
        if (exception.hasLogMessage()) {
            log.error(exception.getMessage());
        }
    }

    private ResponseEntity<ErrorResponse> toResponse(GlobalException exception) {
        ExceptionType exceptionType = exception.getExceptionType();
        return ResponseEntity.status(exceptionType.getHttpStatus()).body(ErrorResponseMapper.toResponse(exceptionType));
    }
}
