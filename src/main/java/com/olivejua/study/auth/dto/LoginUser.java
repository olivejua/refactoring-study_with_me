package com.olivejua.study.auth.dto;

import com.olivejua.study.domain.user.User;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class LoginUser {
    private User user;

    public LoginUser(User user) {
        this.user = user;
    }

    public Long getUserId() {
        return user.getId();
    }
}
