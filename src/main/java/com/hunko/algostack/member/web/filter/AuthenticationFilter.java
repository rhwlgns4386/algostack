package com.hunko.algostack.member.web.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hunko.algostack.member.domain.vo.MemberInfo;
import com.hunko.algostack.member.exception.ErrorStatus;
import com.hunko.algostack.member.exception.JwtAuthenticationException;
import com.hunko.algostack.member.web.provider.TokenProvider;
import com.hunko.algostack.shared.common.exception.mapper.ErrorResponseMapper;
import com.hunko.algostack.shared.common.exception.response.ErrorResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;
    private final ErrorHandler errorHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Optional<String> token = tokenProvider.getToken(request);

        if(token.isPresent()){
            String tokenValue = token.get();
            MemberInfo memberInfo;
            try {
               memberInfo = tokenProvider.toInfo(tokenValue);
            }catch (JwtAuthenticationException singInException){
                errorHandler.writeAuthErrorResponse(response);
                return;
            } catch (Exception e){
                errorHandler.writeServerErrorResponse(response);
                return;
            }

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(memberInfo.email(), null, Collections.emptyList());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request,response);
    }
}
