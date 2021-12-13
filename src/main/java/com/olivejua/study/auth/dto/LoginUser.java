package com.olivejua.study.auth.dto;

import com.olivejua.study.domain.user.Role;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class LoginUser {
    private Long id;
    private Role role = Role.USER;

    public LoginUser(Long id) {
        this.id = id;
    }
}
