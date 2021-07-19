package com.olivejua.study.integration.repository;

import com.olivejua.study.domain.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserRepositoryTest extends CommonRepositoryTest {

    @AfterEach
    void clearAll() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("모든 usernames을 가져온다")
    public void testFindAllNames() {
        int expectedSize = 15;

        for (int i=0; i<expectedSize; i++) {
            createUser();
        }

        List<String> usernames = userRepository.findAllNames();

        assertEquals(expectedSize, usernames.size());
    }
    
    @Test
    @DisplayName("email과 socialcode로 user 찾기")
    public void testFindByEmailAndSocialCode() {
        //given
        User sampleUser = createUser();
        String email = sampleUser.getEmail();
        String socialCode = sampleUser.getSocialCode();

        //when
        User findUser = userRepository.findByEmailAndSocialCode(email, socialCode).orElse(null);

        //then
        assertNotNull(findUser);
        assertEquals(sampleUser.getId(), findUser.getId());
        assertEquals(sampleUser.getName(), findUser.getName());
        assertEquals(sampleUser.getRole(), findUser.getRole());
    }
}