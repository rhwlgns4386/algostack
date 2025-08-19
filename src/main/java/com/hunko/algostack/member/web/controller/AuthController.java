package com.hunko.algostack.member.web.controller;

import com.hunko.algostack.member.domain.entity.Email;
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

        return toAuthLoginResponse(memberInfo);
    }

    @PostMapping("/singin")
    @ResponseStatus(HttpStatus.CREATED)
    public void singin(@Valid @RequestBody AuthSingInRequest authLoginRequest) {
        authService.singIn(toCommand(authLoginRequest));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthLoginResponse> refresh(@RequestHeader(HttpHeaders.AUTHORIZATION) String refreshToken) {

        String email = tokenProvider.getEmailFromToken(refreshToken);
        String jti = tokenProvider.getJtiFromToken(refreshToken);

        MemberInfo memberInfo = authService.refresh(jti, new Email(email));

        return toAuthLoginResponse(memberInfo);
    }

    @DeleteMapping("/logout")
    public void logout(@RequestHeader(HttpHeaders.AUTHORIZATION) String refreshToken) {
        String jti = tokenProvider.getJtiFromToken(refreshToken);
        authService.addBlackList(jti);
    }

    private ResponseEntity<AuthLoginResponse> toAuthLoginResponse(MemberInfo memberInfo) {
        ResponseCookie cookie = tokenProvider.refreshTokenCookie(memberInfo);
        String accessToken = tokenProvider.createAccessToken(memberInfo);
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(toLoginResponse(accessToken, memberInfo.nickname()));
    }
}
