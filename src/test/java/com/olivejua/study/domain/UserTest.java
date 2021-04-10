package com.olivejua.study.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class UserTest {

    @Autowired
    TestEntityManager em;

    @Test
    @DisplayName("사용자 생성")
    void createWriter() {
        User user = User.builder()
                .name("김슬기")
                .email("tmfrl4710@gmail.com")
                .role(Role.GUEST)
                .socialCode("google")
                .build();

        em.persist(user);
        User findUser = em.find(User.class, user.getId());

        assertEquals(user.getId(), findUser.getId());
        assertEquals(user.getName(), findUser.getName());
        assertEquals(user.getEmail(), findUser.getEmail());
        assertEquals(user.getRole(), findUser.getRole());
        assertEquals(user.getSocialCode(), findUser.getSocialCode());
    }

    @Test
    @DisplayName("회원가입")
    void join() {
        //given
        User user = User.builder()
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