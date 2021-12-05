package com.olivejua.study.domain;

import java.util.Arrays;

public enum SocialCode {
    GOOGLE,
    NAVER
    ;

    public static SocialCode findByName(String name) {
        String upperName = name.toUpperCase();

        return Arrays.stream(values())
                .filter(value -> upperName.equals(value.name()))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("SocialCode를 찾을 수 없습니다. SocialCode=" + name));
    }
}
