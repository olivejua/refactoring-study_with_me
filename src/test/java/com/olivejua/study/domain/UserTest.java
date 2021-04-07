package com.olivejua.study.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    @DisplayName("사용자 생성")
    void createUser() {
        User user = User.builder()
                .id(1L)
                .name("김슬기")
                .email("tmfrl4710@gmail.com")
                .role(Role.GUEST)
                .socialCode("google")
                .build();

        assertEquals(1L, user.getId());
        assertEquals("김슬기", user.getName());
        assertEquals("tmfrl4710@gmail.com", user.getEmail());
        assertEquals(Role.GUEST, user.getRole());
        assertEquals("google", user.getSocialCode());
    }

    @Test
    @DisplayName("회원가입")
    void join() {
        //given
        User user = User.builder()
                .id(1L)
                .name("김슬기")
                .email("tmfrl4710@gmail.com")
                .role(Role.GUEST)
                .socialCode("google")
                .build();

        //when
        user.join();

        //then
        assertEquals(Role.USER, user.getRole());
    }

    @Test
    @DisplayName("프로필 수정")
    void changeProfile() {
        User user = User.builder()
                .id(1L)
                .name("김슬기")
                .email("tmfrl4710@gmail.com")
                .role(Role.GUEST)
                .socialCode("google")
                .build();

        String changedName = "olivejua";
        user.changeProfile(changedName);

        assertEquals(changedName, user.getName());
    }
}