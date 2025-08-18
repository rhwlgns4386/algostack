package com.hunko.algostack.member.config;

import com.hunko.algostack.member.web.filter.AuthenticationFilter;
import com.hunko.algostack.member.web.provider.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class AuthConfig {

    private final TokenProvider tokenProvider;

    @Order(1)
    @Bean
    public SecurityFilterChain authFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .securityMatcher("/auth/**")
                .authorizeHttpRequests(auth ->
                        auth.anyRequest().permitAll()
                )
                .build();
    }

    @Order(2)
    @Bean
    public SecurityFilterChain defaultFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->
                        auth.anyRequest().authenticated()
                ).addFilterBefore(new AuthenticationFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
