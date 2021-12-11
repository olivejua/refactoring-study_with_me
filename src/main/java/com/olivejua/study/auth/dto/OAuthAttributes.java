package com.olivejua.study.auth.dto;

import com.olivejua.study.domain.user.Role;
import com.olivejua.study.domain.user.SocialCode;
import com.olivejua.study.domain.user.User;
import lombok.Getter;

import java.util.Map;

import static com.olivejua.study.domain.user.SocialCode.*;

@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private SocialCode socialCode;

    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, SocialCode socialCode) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.socialCode = socialCode;
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        if(NAVER.equals(registrationId)) {
            return ofNaver("id", attributes);
        }

        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return new OAuthAttributes(
                attributes,
                userNameAttributeName,
                (String) attributes.get("name"),
                (String) attributes.get("email"),
                GOOGLE );
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return new OAuthAttributes(
                response,
                userNameAttributeName,
                (String) response.get("name"),
                (String) response.get("email"),
                NAVER);
    }

    public User toEntity() {
        return User.createUser(
                name,
                email,
                Role.GUEST,
                socialCode
        );
    }
}

