package com.hunko.algostack.member.web.controller;

import com.hunko.algostack.member.domain.service.AuthService;
import com.hunko.algostack.member.domain.vo.MemberInfo;
import com.hunko.algostack.member.web.dto.AuthLoginRequest;
import com.hunko.algostack.member.web.dto.AuthLoginResponse;
import com.hunko.algostack.member.web.dto.AuthSingInRequest;
import com.hunko.algostack.member.web.provider.TokenProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.hunko.algostack.member.web.mapper.CommandMapper.toCommand;
import static com.hunko.algostack.member.web.mapper.CommandMapper.toLoginResponse;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final TokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<AuthLoginResponse> login(@Valid @RequestBody AuthLoginRequest authLoginRequest) {
        MemberInfo memberInfo = authService.login(toCommand(authLoginRequest));

        ResponseCookie cookie = tokenProvider.refreshTokenCookie(memberInfo);
        String accessToken = tokenProvider.createAccessToken(memberInfo);
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(toLoginResponse(accessToken, memberInfo.nickname()));
    }

    @PostMapping("/singin")
    @ResponseStatus(HttpStatus.CREATED)
    public void singin(@Valid @RequestBody AuthSingInRequest authLoginRequest) {
        authService.singIn(toCommand(authLoginRequest));
    }

}
