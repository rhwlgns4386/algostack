package com.hunko.algostack.member.config;

import com.hunko.algostack.member.util.PasswordUtil;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class PasswordUtilConfig {

    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        PasswordUtil.setPasswordEncoder(passwordEncoder);
    }
}
