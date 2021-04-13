package com.olivejua.study.sampleData;

import com.olivejua.study.domain.Role;
import com.olivejua.study.domain.User;

public class SampleUser {
    public static User create() {
        return User.builder()
                .name("김슬기")
                .email("tmfrl4710@gmail.com")
                .role(Role.GUEST)
                .socialCode("google")
                .build();
    }
}
