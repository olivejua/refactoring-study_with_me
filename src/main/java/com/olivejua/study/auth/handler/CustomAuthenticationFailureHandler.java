package com.olivejua.study.auth.handler;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.olivejua.study.utils.ApiUrlPaths.USERS;
import static com.olivejua.study.utils.ApiUrlPaths.Users;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        System.out.println("CustomAuthenticationFailureHandler.onAuthenticationFailure()");
        System.out.println("OAuth 로그인 실패");

        response.sendRedirect(USERS + Users.SIGN_UP);
    }
}
