package com.olivejua.study.sampleData;

import com.olivejua.study.domain.Role;
import com.olivejua.study.domain.User;

import java.util.ArrayList;
import java.util.List;

public class SampleUser {
    public static User create() {
        return User.createUser(
                "김슬기",
                "tmfrl4710@gmail.com",
                Role.GUEST,
                "google"
        );
    }

    public static List<User> createList(int size) {
        List<User> result = new ArrayList<>();

        for(int i=1; i<=size; i++) {
            result.add(
                    User.createUser(
                            "user"+i,
                            "user" + i + "@gmail.com",
                            Role.GUEST,
                            "google"
                    )
            );
        }

        return result;
    }
}
