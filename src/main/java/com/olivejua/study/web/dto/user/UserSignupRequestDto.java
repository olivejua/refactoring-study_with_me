package com.olivejua.study.web.dto.user;

import com.olivejua.study.domain.Role;
import com.olivejua.study.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserSignupRequestDto {
    private String name;
    private String email;
    private String socialCode;

    @Builder
    public UserSignupRequestDto(String name, String email, String socialCode) {
        this.name = name;
        this.email = email;
        this.socialCode = socialCode;
    }

    public User toEntity() {
        return User.createUser(
                name, email, Role.USER, socialCode
        );
    }
}
