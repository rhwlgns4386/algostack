package com.hunko.algostack.member.domain.entity;

import com.hunko.algostack.member.util.PasswordUtil;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Password {

    private String password;

    public boolean isSamePassword(String input) {
        return PasswordUtil.isSamePassword(input, password);
    }

    public static Password from(String password) {
        String encode = PasswordUtil.encode(password);
        return new Password(encode);
    }
}
