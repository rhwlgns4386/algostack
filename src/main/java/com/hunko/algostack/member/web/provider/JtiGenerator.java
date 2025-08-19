package com.hunko.algostack.member.web.provider;

import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class JtiGenerator {

    private static final int SEQ_BITS = 12;
    private static AtomicInteger sequence = new AtomicInteger(0);
    private static AtomicLong lastedTimeStamp = new AtomicLong(System.currentTimeMillis());

    public static JtiId createJtiId(Key key) {
        long timeStamp = System.currentTimeMillis();
        long lastTime = lastedTimeStamp.get();

        if (lastTime != timeStamp) {
            if (lastedTimeStamp.compareAndSet(lastTime, timeStamp)) {
                sequence.set(0);
            }
        }

        int seq = sequence.getAndIncrement();

        BigInteger jti = BigInteger.valueOf(timeStamp).shiftLeft(76)
                .or(BigInteger.valueOf(key.getBit()).shiftLeft(12))
                .or(BigInteger.valueOf(seq & 0xFFF));

        return new JtiId(jti);
    }
}
