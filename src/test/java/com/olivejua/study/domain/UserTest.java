package com.olivejua.study.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class UserTest {

    @Autowired
    EntityManager em;

    @Test
    @DisplayName("사용자 생성")
    void createWriter() {
        User user =
                User.createUser(
                        "김슬기",
                        "tmfrl4710@gmail.com",
                        Role.GUEST,
                        "google"
                );

        em.persist(user);

        em.flush();
        em.clear();

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
        User user = User.createUser(
                "김슬기",
                "tmfrl4710@gmail.com",
                Role.GUEST,
                "google"
        );

        //when
        user.changeRoleToUser();

        //then
        assertEquals(Role.USER, user.getRole());
    }

    @Test
    @DisplayName("프로필 수정")
    void changeProfile() {
        User user = User.createUser(
                "김슬기",
                "tmfrl4710@gmail.com",
                Role.GUEST,
                "google"
        );

        String changedName = "olivejua";
        user.changeProfile(changedName);

        assertEquals(changedName, user.getName());
    }
}