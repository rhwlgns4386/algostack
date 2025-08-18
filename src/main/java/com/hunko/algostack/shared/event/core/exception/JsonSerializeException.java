package com.hunko.algostack.shared.event.core.exception;

import com.fasterxml.jackson.core.JsonProcessingException;

public class JsonSerializeException extends RuntimeException {
    public JsonSerializeException(String message, JsonProcessingException e) {
        super(message, e);
    }
}
