package com.olivejua.study.auth;

import com.olivejua.study.auth.dto.AuthenticatedUser;
import com.olivejua.study.auth.dto.OAuthAttributes;
import com.olivejua.study.domain.user.User;
import com.olivejua.study.exception.user.NotFoundAuthenticationException;
import com.olivejua.study.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> userService = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = userService.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes
                .of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        User user = verifyUser(attributes);

        return new AuthenticatedUser(user);
    }

    private User verifyUser(OAuthAttributes authAttributes) {
        return userRepository
                .findByEmailAndSocialCode(authAttributes.getEmail(), authAttributes.getSocialCode())
                .orElseThrow(() -> new NotFoundAuthenticationException(authAttributes));
    }
}