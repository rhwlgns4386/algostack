package com.hunko.algostack.algorithm.util;

import com.hunko.algostack.algorithm.domain.entity.UserId;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@UtilityClass
public class SecurityUtil {

    public static UserId getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return new UserId((String) authentication.getPrincipal());
    }
}
