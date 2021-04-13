package com.olivejua.study.repository;

import com.olivejua.study.domain.User;
import com.olivejua.study.sampleData.SampleUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("User - 저장")
    public void save() {
        User user = SampleUser.create();
        userRepository.save(user);

        User findUser = userRepository.findAll().get(0);

        assertEquals(user, findUser);
        assertEquals(user.getId(), findUser.getId());
    }
}