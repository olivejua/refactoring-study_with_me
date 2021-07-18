package com.olivejua.study.integration.repository;

import com.olivejua.study.domain.Role;
import com.olivejua.study.domain.User;
import com.olivejua.study.integration.repository.CommonRepositoryTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserRepositoryTest extends CommonRepositoryTest {

    @Test
    @DisplayName("user를 repository에 저장한다")
    public void save() {
        //given
        User user = saveUser();

        //when
        userRepository.save(user);


        User findUser = userRepository.findAll().get(0);

        assertEquals(user, findUser);
        assertEquals(user.getId(), findUser.getId());
    }

    private User saveUser() {
        return User.createUser(
                "sample username", "user@gmail.com", Role.GUEST, "google");
    }

    @Override
    void setup() {

    }

    @Override
    void clearAll() {

    }
}