package com.olivejua.study.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum SocialCode {
    GOOGLE("google"),
    NAVER("naver")
    ;

    private final String registrationId;

    public boolean equals(String registrationId) {
        return this.registrationId.equals(registrationId);
    }

    public static SocialCode findByName(String name) {
        String upperName = name.toUpperCase();

        return Arrays.stream(values())
                .filter(value -> upperName.equals(value.name()))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("SocialCode를 찾을 수 없습니다. SocialCode=" + name));
    }
}
