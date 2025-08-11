package com.hunko.algostack.algorithm.domain.entity;

public enum Result {
    SUCCESS,
    FAIL,
    ;

    public boolean isSuccess() {
        return SUCCESS.equals(this);
    }
}
