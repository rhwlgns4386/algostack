package com.hunko.algostack.algorithm.domain.event.exception;

public class JsonDeSerializeException extends RuntimeException {
    public JsonDeSerializeException(String message, Exception e) {
        super(message,e);
    }
}
