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

import static com.hunko.algostack.member.web.provider.JtiGenerator.createJtiId;

@Component
@RequiredArgsConstructor
public class TokenProvider {

    private static final String AUTH_HEADER = HttpHeaders.AUTHORIZATION;
    private static final String BEARER = "Bearer ";

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.access-token-validity}")
    private int accessTokenExpireInSeconds;
    @Value("${jwt.refresh-token-validity}")
    private int refreshTokenExpireInSeconds;

    public ResponseCookie refreshTokenCookie(MemberInfo memberInfo) {
        return ResponseCookie.from("refreshToken", createRefreshToken(memberInfo))
                .sameSite("Lax")
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
                .id(createJti(memberInfo))
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
        String email = getEmailFromToken(token);
        return new MemberInfo(null, email, null);
    }

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public Optional<String> getToken(HttpServletRequest request) {
        String header = request.getHeader(AUTH_HEADER);
        return getToken(header);
    }

    public Optional<String> getToken(String header) {
        if (header == null || !header.startsWith(BEARER)) {
            return Optional.empty();
        }
        return Optional.of(header.substring(BEARER.length()));
    }

    public String getEmailFromToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(getKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return claims.getSubject();
        } catch (JwtException e) {
            throw new JwtAuthenticationException(e);
        }
    }

    public String getJtiFromToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(getKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return claims.getId();
        } catch (JwtException e) {
            throw new JwtAuthenticationException(e);
        }
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

    private static String createJti(MemberInfo memberInfo) {
        return createJtiId(new UserIdKey(memberInfo.userId())).getJti();
    }
}
