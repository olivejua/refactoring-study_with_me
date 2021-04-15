package com.olivejua.study.web.dto.user;

import com.olivejua.study.domain.Role;
import com.olivejua.study.domain.User;
import lombok.Getter;

@Getter
public class UserSignupResponseDto {
    private String name;
    private String email;
    private Role role;
    private String socialCode;

    public UserSignupResponseDto(User entity) {
        this.name = entity.getName();
        this.email = entity.getEmail();
        this.role = entity.getRole();
        this.socialCode = entity.getSocialCode();
    }
}
