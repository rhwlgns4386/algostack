package com.hunko.algostack.member.web.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hunko.algostack.member.domain.vo.MemberInfo;
import com.hunko.algostack.member.exception.SingInException;
import com.hunko.algostack.member.web.provider.TokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Optional<String> token = tokenProvider.getToken(request);

        try {
            token.ifPresent(tokenValue -> {
                MemberInfo memberInfo = tokenProvider.toInfo(tokenValue);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(new User(
                        memberInfo.email(), null, Collections.emptyList()
                ), Collections.emptyList());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            });

            filterChain.doFilter(request,response);
        }catch (SingInException singInException){
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } catch (Exception e){
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
