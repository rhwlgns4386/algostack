package com.hunko.algostack.member.web.provider;

import com.hunko.algostack.member.domain.vo.MemberInfo;
import com.hunko.algostack.member.exception.JwtAuthenticationException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TokenProvider {

    private static final String AUTH_HEADER = HttpHeaders.AUTHORIZATION;
    private static final String BEARER = "Bearer ";

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.access-token-validity}")
    private int accessTokenExpireInSeconds = 3600;
    @Value("${jwt.refresh-token-validity}")
    private int refreshTokenExpireInSeconds = 3600;

    public ResponseCookie refreshTokenCookie(MemberInfo memberInfo) {
        return ResponseCookie.from("refreshToken", createRefreshToken(memberInfo))
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(refreshTokenExpireInSeconds)
                .build();
    }

    private String createRefreshToken(MemberInfo memberInfo) {
        SecretKey key = getKey();
        return Jwts.builder()
                .signWith(key)
                .subject(memberInfo.email())
                .issuedAt(issuedAt())
                .expiration(expiresAt(refreshTokenExpireInSeconds))
                .compact();
    }

    public String createAccessToken(MemberInfo memberInfo) {
        SecretKey key = getKey();
        return Jwts.builder()
                .signWith(key)
                .subject(memberInfo.email())
                .claim("nickname", memberInfo.nickname())
                .issuedAt(issuedAt())
                .expiration(expiresAt(accessTokenExpireInSeconds))
                .compact();
    }

    public MemberInfo toInfo(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(getKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            String email = claims.getSubject();
            return new  MemberInfo(email, null);
        }catch (ExpiredJwtException e){
            throw new JwtAuthenticationException(e);
        }
    }

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    private Date issuedAt() {
        LocalDateTime localDateTime = LocalDateTime.now();
        return toDate(localDateTime);
    }

    private Date expiresAt(int expiresIn) {
        LocalDateTime localDateTime = LocalDateTime.now().plusSeconds(expiresIn);
        return toDate(localDateTime);
    }

    private Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public Optional<String> getToken(HttpServletRequest request) {
        String header = request.getHeader(AUTH_HEADER);
        if (header == null || !header.startsWith(BEARER)) {
            return Optional.empty();
        }
        return Optional.of(header.substring(BEARER.length()));
    }
}
