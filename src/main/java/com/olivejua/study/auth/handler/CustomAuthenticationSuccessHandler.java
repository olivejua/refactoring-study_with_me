package com.olivejua.study.auth.handler;

import com.olivejua.study.auth.JwtTokenProvider;
import com.olivejua.study.auth.dto.AuthenticatedUser;
import com.olivejua.study.domain.auth.AuthToken;
import com.olivejua.study.repository.AuthTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthTokenRepository authTokenRepository;

    private final String ACCESS_TOKEN = "access-token";
    private final String REFRESH_TOKEN = "refresh-token";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        AuthenticatedUser authenticatedUser = (AuthenticatedUser) authentication.getPrincipal();
        String accessToken = jwtTokenProvider.createTokenForUser(authenticatedUser.getId());
        String refreshToken = jwtTokenProvider.createRefreshTokenForUser(authenticatedUser.getId());

        updateRefreshToken(authenticatedUser, refreshToken);

        response.addHeader(ACCESS_TOKEN, accessToken);
        response.addHeader(REFRESH_TOKEN, refreshToken);
    }

    private void updateRefreshToken(AuthenticatedUser authenticatedUser, String refreshToken) {
        authTokenRepository.findById(authenticatedUser.getId()).ifPresentOrElse(
                authToken -> {
                    authToken.updateRefreshToken(refreshToken);
                    authTokenRepository.save(authToken);
                },
                () -> authTokenRepository.save(AuthToken.createAuthToken(authenticatedUser.toEntity(), refreshToken))
        );
    }
}