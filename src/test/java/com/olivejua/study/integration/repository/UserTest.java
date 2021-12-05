package com.olivejua.study.integration.repository;

import com.olivejua.study.domain.User;
import com.olivejua.study.integration.IntegrationTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserTest extends IntegrationTest {

    @Test
    void save() {
        User user = userFactory.user();

        assertNotNull(user.getId());
    }
}
