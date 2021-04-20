package com.olivejua.study.web.dto.user;

import com.olivejua.study.domain.User;
import lombok.Getter;

@Getter
public class UserSignInResponseDto {
    private Long id;
    private String name;

    public UserSignInResponseDto(User entity) {
        this.id = entity.getId();
        this.name = entity.getName();
    }
}