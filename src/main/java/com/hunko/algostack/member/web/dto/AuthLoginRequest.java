package com.hunko.algostack.member.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AuthLoginRequest(
        @Email
        String email,
        @NotBlank
        @Pattern(regexp = "^(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&_-])[A-Za-z\\d@$!%*?&_-]{8,}$")
        String password
) {
}
