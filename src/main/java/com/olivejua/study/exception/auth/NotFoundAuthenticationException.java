package com.olivejua.study.exception.auth;

import com.olivejua.study.auth.dto.OAuthAttributes;
import com.olivejua.study.domain.user.SocialCode;
import org.springframework.security.authentication.AuthenticationServiceException;

public class NotFoundAuthenticationException extends AuthenticationServiceException {

    private final String userEmail;
    private final String username;
    private final SocialCode socialCode;
    private static final String MESSAGE = "회원정보를 찾을 수 없습니다";

    public NotFoundAuthenticationException(OAuthAttributes authAttributes) {
        super(MESSAGE);

        userEmail = authAttributes.getEmail();
        username = authAttributes.getName();
        socialCode = authAttributes.getSocialCode();
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUsername() {
        return username;
    }

    public SocialCode getSocialCode() {
        return socialCode;
    }
}
