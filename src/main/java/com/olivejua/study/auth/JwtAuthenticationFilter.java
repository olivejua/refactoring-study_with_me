package com.olivejua.study.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("JwtAuthenticationFilter.doFilterInternal()");
        String jwtHeader = jwtTokenProvider.resolveToken(request);

        if (isBearerToken(jwtHeader) && jwtTokenProvider.validateToken(extractJwtToken(jwtHeader))) {
            Authentication authentication = jwtTokenProvider.getAuthentication(extractJwtToken(jwtHeader));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private boolean isBearerToken(String jwtHeader) {
        return jwtHeader!=null && jwtHeader.startsWith("Bearer ");
    }

    private String extractJwtToken(String bearerToken) {
        return bearerToken.replace("Bearer ", "");
    }
}