package com.hunko.algostack.member.web.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hunko.algostack.member.exception.ErrorStatus;
import com.hunko.algostack.shared.common.exception.mapper.ErrorResponseMapper;
import com.hunko.algostack.shared.common.exception.response.ErrorResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;

@RequiredArgsConstructor
public class ErrorHandler {

    private final ObjectMapper objectMapper;

    public void writeAuthErrorResponse(HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        ErrorStatus authenticationFail = ErrorStatus.AUTHENTICATION_FAIL;
        ErrorResponse responseBody = ErrorResponseMapper.toResponse(authenticationFail);
        response.getWriter().write(objectMapper.writeValueAsString(responseBody));
    }

    public void writeServerErrorResponse(HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        response.getWriter().write(objectMapper.writeValueAsString(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())));
    }
}
