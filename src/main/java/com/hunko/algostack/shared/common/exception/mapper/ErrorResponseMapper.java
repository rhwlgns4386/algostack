package com.hunko.algostack.shared.common.exception.mapper;

import com.hunko.algostack.shared.common.exception.ExceptionType;
import com.hunko.algostack.shared.common.exception.GlobalException;
import com.hunko.algostack.shared.common.exception.response.ErrorResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ErrorResponseMapper {

    public ErrorResponse toResponse(ExceptionType exceptionType) {
        return new ErrorResponse(exceptionType.getHttpStatus(), exceptionType.getMessage());
    }
}
