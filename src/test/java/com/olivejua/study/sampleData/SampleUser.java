package com.olivejua.study.sampleData;

import com.olivejua.study.domain.Role;
import com.olivejua.study.domain.User;

import java.util.ArrayList;
import java.util.List;

public class SampleUser {
    public static User create() {
        return User.builder()
                .name("김슬기")
                .email("tmfrl4710@gmail.com")
                .role(Role.GUEST)
                .socialCode("google")
                .build();
    }

    public static List<User> createList(int size) {
        List<User> result = new ArrayList<>();

        for(int i=1; i<=size; i++) {
            result.add(
                    User.builder()
                        .name("user"+i)
                        .email("user" + i + "@gmail.com")
                        .role(Role.GUEST)
                        .socialCode("google")
                        .build()
            );
        }

        return result;
    }
}
