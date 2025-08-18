package com.hunko.algostack.algorithm.domain.event.exception;

import com.fasterxml.jackson.core.JsonProcessingException;

public class JsonSerializeException extends RuntimeException {
    public JsonSerializeException(String message, JsonProcessingException e) {
        super(message, e);
    }
}
