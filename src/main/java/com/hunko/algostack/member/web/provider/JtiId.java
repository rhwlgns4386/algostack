package com.hunko.algostack.member.web.provider;

import lombok.RequiredArgsConstructor;

import java.math.BigInteger;

@RequiredArgsConstructor
public class JtiId {

    private final BigInteger jti;

    public String getJti() {
        return jti.toString(32);
    }
}
