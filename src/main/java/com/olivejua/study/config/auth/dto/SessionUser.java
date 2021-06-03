package com.olivejua.study.config.auth.dto;

import com.olivejua.study.domain.Role;
import com.olivejua.study.domain.User;

public class SessionUser {
    private Long id;
    private String name;
    private String email;
    private Role role;
    private String socialCode;

    public SessionUser(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.role = user.getRole();
        this.socialCode = user.getSocialCode();
    }

    public User toEntity() {
        return User.createUser(
                name,
                email,
                role,
                socialCode
        );
    }
}
