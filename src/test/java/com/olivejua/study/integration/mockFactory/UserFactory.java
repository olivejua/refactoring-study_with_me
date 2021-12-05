package com.olivejua.study.integration.mockFactory;

import com.olivejua.study.domain.Role;
import com.olivejua.study.domain.User;
import com.olivejua.study.common.mockData.MockUser;
import com.olivejua.study.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;

import static com.olivejua.study.domain.Role.USER;
import static com.olivejua.study.domain.SocialCode.GOOGLE;

@TestComponent
public class UserFactory {
    @Autowired
    private UserRepository userRepository;

    /****************************************************************************
     * Create a User
     ***************************************************************************/
    public User user() {
        User user = MockUser.builder()
                .name("Seulki")
                .email("seulki@gmail.com")
                .role(USER)
                .socialCode(GOOGLE)
                .build();

        return userRepository.save(user);
    }

    public User user(String name) {
        User user = MockUser.builder()
                .name(name)
                .email(name + "@gmail.com")
                .role(USER)
                .socialCode(GOOGLE)
                .build();

        return userRepository.save(user);
    }

    public User user(String name, Role role) {
        User user = MockUser.builder()
                .name(name)
                .email(name + "@gmail.com")
                .role(role)
                .socialCode(GOOGLE)
                .build();

        return userRepository.save(user);
    }
}
