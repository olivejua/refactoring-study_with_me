package com.olivejua.study.integration.repository;

import com.olivejua.study.config.QueryRepositoryConfig;
import com.olivejua.study.domain.Comment;
import com.olivejua.study.domain.Role;
import com.olivejua.study.domain.User;
import com.olivejua.study.domain.board.Question;
import com.olivejua.study.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

@DataJpaTest
@Import(QueryRepositoryConfig.class)
public abstract class CommonRepositoryTest {

    @Autowired
    protected UserRepository userRepository;

    private static int userIndex = 1;

    protected User createUser() {
        String username = "sampleUser" + (userIndex++);

        User user = User.createUser(
                username, username + "@gmail.com", Role.USER, "google");

        return userRepository.save(user);
    }
}
