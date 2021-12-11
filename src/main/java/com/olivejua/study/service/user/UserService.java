package com.olivejua.study.service.user;

import com.olivejua.study.auth.dto.AuthenticatedUser;
import com.olivejua.study.domain.user.User;
import com.olivejua.study.exception.user.NotFoundUserException;
import com.olivejua.study.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        long id = Long.parseLong(userId);
        User user = userRepository.findById(id)
                .orElseThrow(NotFoundUserException::new);

        return new AuthenticatedUser(user);
    }
}
