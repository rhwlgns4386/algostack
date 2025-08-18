package com.hunko.algostack.shared.event.core.exception;

public class JsonDeSerializeException extends RuntimeException {
    public JsonDeSerializeException(String message, Exception e) {
        super(message,e);
    }
}
