package com.hunko.algostack.shared.common.exception.handler;

import com.hunko.algostack.shared.common.exception.ExceptionType;
import com.hunko.algostack.shared.common.exception.GlobalException;
import com.hunko.algostack.shared.common.exception.mapper.ErrorResponseMapper;
import com.hunko.algostack.shared.common.exception.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> exception(MethodArgumentNotValidException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(HttpStatus.BAD_REQUEST,"잘못된 입력입니다."));
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
