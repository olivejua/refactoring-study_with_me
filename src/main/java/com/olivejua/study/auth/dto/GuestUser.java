package com.olivejua.study.auth.dto;

import com.olivejua.study.domain.user.Role;
import com.olivejua.study.domain.user.SocialCode;
import lombok.ToString;

@ToString
public class GuestUser {
    private String name;
    private String email;
    private SocialCode socialCode;
    private final Role role = Role.GUEST;

    public GuestUser(String name, String email, SocialCode socialCode) {
        this.name = name;
        this.email = email;
        this.socialCode = socialCode;
    }
}
