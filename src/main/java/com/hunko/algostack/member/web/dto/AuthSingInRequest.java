package com.hunko.algostack.member.web.dto;

import jakarta.validation.constraints.*;

public record AuthSingInRequest(
        @Email
        String email,
        @NotBlank
        @Pattern(regexp = "^(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&_-])[A-Za-z\\d@$!%*?&_-]{8,}$")
        String password,
        @NotBlank
        String nickName
) {
}
