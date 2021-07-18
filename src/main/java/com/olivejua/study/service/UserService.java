package com.olivejua.study.service;

import com.olivejua.study.config.auth.dto.SessionUser;
import com.olivejua.study.domain.User;
import com.olivejua.study.exception.NotExistsUserException;
import com.olivejua.study.repository.UserRepository;
import com.olivejua.study.web.dto.user.UserSignupRequestDto;
import com.olivejua.study.web.dto.user.UserSignupResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Transactional(readOnly = true)
    public List<String> findAllNames() {
        return userRepository.findAllNames();
    }

    public UserSignupResponseDto signUp(UserSignupRequestDto requestDto) {
        User user = requestDto.toEntity();
        user.changeRoleToUser();

        User savedUser = userRepository.save(user);
        httpSession.setAttribute("user", new SessionUser(savedUser));

        return new UserSignupResponseDto(savedUser);
    }

    public void changeProfile(Long userId, String name) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotExistsUserException("해당 User가 없습니다."));

        user.changeProfile(name);
    }
}
