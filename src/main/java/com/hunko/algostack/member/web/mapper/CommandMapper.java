package com.hunko.algostack.member.web.mapper;

import com.hunko.algostack.member.domain.entity.Email;
import com.hunko.algostack.member.domain.vo.LoginCommand;
import com.hunko.algostack.member.domain.vo.SingInCommand;
import com.hunko.algostack.member.web.dto.AuthLoginRequest;
import com.hunko.algostack.member.web.dto.AuthLoginResponse;
import com.hunko.algostack.member.web.dto.AuthSingInRequest;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CommandMapper {

    public static LoginCommand toCommand(AuthLoginRequest authLoginRequest) {
        return new LoginCommand(new Email(authLoginRequest.email()), authLoginRequest.password());
    }

    public static AuthLoginResponse toLoginResponse(String accessToken, String nickname) {
        return new AuthLoginResponse(accessToken, nickname);
    }

    public static SingInCommand toCommand(AuthSingInRequest request) {
        return new SingInCommand(request.email(),request.password(),request.nickName());
    }
}
