package com.hunko.algostack.member.web.provider;

public class UserIdKey implements Key {

    private Long userId;

    public UserIdKey(Long userId) {
        this.userId = userId;
    }

    @Override
    public long bitSize() {
        return 64;
    }

    @Override
    public long getBit() {
        return userId;
    }
}
