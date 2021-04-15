package com.olivejua.study.service;

import com.olivejua.study.domain.Role;
import com.olivejua.study.domain.User;
import com.olivejua.study.repository.UserRepository;
import com.olivejua.study.sampleData.SampleUser;
import com.olivejua.study.web.dto.user.UserSignInResponseDto;
import com.olivejua.study.web.dto.user.UserSignupRequestDto;
import com.olivejua.study.web.dto.user.UserSignupResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Test
    void findAllNames() {
        //given
        List<User> users = SampleUser.createList(5);
        List<String> usernames = users.stream()
                .map(user -> {
                    userRepository.save(user);
                    return user.getName();
                })
                .collect(Collectors.toList());


        //when
        List<String> findUsernames = userService.findAllNames();

        //then
        assertEquals(usernames, findUsernames);
    }

    @Test
    void join() {
        //given
        UserSignupRequestDto requestDto = UserSignupRequestDto.builder()
                .name("olivejua")
                .email("dreamingoctopus@gmail.com")
                .socialCode("google")
                .build();

        //when
        UserSignupResponseDto responseDto = userService.join(requestDto);

        //then
        System.out.println(responseDto.getId());
        assertEquals(requestDto.getName(), responseDto.getName());
        assertEquals(requestDto.getEmail(), responseDto.getEmail());
        assertEquals(Role.USER, responseDto.getRole());
        assertEquals(requestDto.getSocialCode(), responseDto.getSocialCode());
    }

    @Test
    void changeProfile_o() {
        //given
        User user = SampleUser.create();
        userRepository.save(user);

        //when
        String changedName = "olivejua-수정";
        UserSignInResponseDto responseDto = userService.changeProfile(user.getId(), changedName);

        //then
        assertEquals(changedName, responseDto.getName());
    }

    @Test
    void changeProfile_x() {
        assertThrows(IllegalArgumentException.class,
                () -> userService.changeProfile(1L, "olivejua-수정"));
    }
}