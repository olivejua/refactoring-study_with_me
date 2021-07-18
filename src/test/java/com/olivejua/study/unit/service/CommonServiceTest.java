package com.olivejua.study.unit.service;

import com.olivejua.study.domain.Role;
import com.olivejua.study.domain.User;
import com.olivejua.study.service.CommentService;
import com.olivejua.study.utils.BoardImageUploader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public abstract class CommonServiceTest {

    @Mock
    protected CommentService commentService;

    @Mock
    protected BoardImageUploader boardImageUploader;

    protected User dummyUser;

    @BeforeEach
    void setupCommon() {
        dummyUser = createUser();
        setup();
    }

    abstract void setup();

    private static int userIndex = 1;

    protected User createUser() {
        String username = "sampleUser" + (userIndex++);
        return User.createUser(username, username+"@gmail.com", Role.USER, "google");
    }
}
