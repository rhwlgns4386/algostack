package com.hunko.algostack.member.web.dto;

public record AuthLoginResponse(
        String accessToken, String nickname
) {
}
