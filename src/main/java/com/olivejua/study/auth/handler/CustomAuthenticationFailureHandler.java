package com.olivejua.study.auth.handler;

import com.olivejua.study.auth.JwtTokenProvider;
import com.olivejua.study.exception.auth.NotFoundAuthenticationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final String ACCESS_TOKEN = "access-token";
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        System.out.println("OAuth 로그인 실패");
        NotFoundAuthenticationException authenticationException = (NotFoundAuthenticationException) exception;
        String accessToken = jwtTokenProvider.createTokenForGuest(
                authenticationException.getUserEmail(),
                authenticationException.getUsername(),
                authenticationException.getSocialCode()
        );
        response.addHeader(ACCESS_TOKEN, accessToken);

        System.out.println("accessToken = " + accessToken);
    }
}
