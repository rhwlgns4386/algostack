package com.hunko.algostack.shared.common.exception.response;

import org.springframework.http.HttpStatus;

public record ErrorResponse(
        HttpStatus httpStatus,
        String message
) {
}
