package com.hunko.algostack.member.util;

import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordUtil {

    @Setter
    private static PasswordEncoder passwordEncoder;

    public static boolean isSamePassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public static String encode(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }
}
