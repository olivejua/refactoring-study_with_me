package com.olivejua.study.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.olivejua.study.service.auth.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationExceptionHandler implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;
    private final ResponseService responseService;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        //status
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        //header
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");

        //body
        String body = objectMapper.writeValueAsString(responseService.getAuthorizationFailResult("unAuthorizedErrorCode", "인증이 필요한 주소입니다. 로그인해주세요."));
        response.getWriter().write(body);
    }

}

