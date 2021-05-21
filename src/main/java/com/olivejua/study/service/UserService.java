package com.olivejua.study.service;

import com.olivejua.study.domain.User;
import com.olivejua.study.repository.UserRepository;
import com.olivejua.study.web.dto.user.UserSignInResponseDto;
import com.olivejua.study.web.dto.user.UserSignupRequestDto;
import com.olivejua.study.web.dto.user.UserSignupResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<String> findAllNames() {
        return userRepository.findAllNames();
    }

    public UserSignupResponseDto join(UserSignupRequestDto requestDto) {
        User user = requestDto.toEntity();
        user.changeRoleToUser();

        User savedUser = userRepository.save(user);

        return new UserSignupResponseDto(savedUser);
    }

    public UserSignInResponseDto changeProfile(Long userId, String name) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 User가 없습니다."));

        user.changeProfile(name);

        return new UserSignInResponseDto(user);
    }
}
