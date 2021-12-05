package com.olivejua.study.config.security;

import com.olivejua.study.domain.user.User;
import com.olivejua.study.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("user를 찾을 수 없습니다. username=" + username));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getName())
                .roles(user.getRole().name())
                .build();
    }
}
