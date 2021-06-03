package com.olivejua.study.config.auth;

import com.olivejua.study.config.auth.dto.OAuthAttributes;
import com.olivejua.study.config.auth.dto.SessionUser;
import com.olivejua.study.domain.User;
import com.olivejua.study.repository.UserRepository;
import com.olivejua.study.service.UserService;
import com.olivejua.study.web.dto.user.UserSignInResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final HttpSession httpSession;
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes
                .of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        User user = verifyUser(attributes);
        httpSession.setAttribute("user", new SessionUser(user));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRole().name())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey()
        );
    }

    //TODO DB에 등록된 회원인지 아닌지 가려내고, 회원이 아닐 경우 로그인페이지 / 회원일 경우 홈으로 이동
    //TODO 이 로직은 컨트롤러 또는 서비스로 이동해야함.
    private User verifyUser(OAuthAttributes attributes) {
        return userRepository.findByEmailAndSocialCode(attributes.getEmail(), attributes.getSocialCode())
                .map(entity -> entity.updateName(attributes.getName()))
                .orElse(attributes.toEntity());
    }
}
