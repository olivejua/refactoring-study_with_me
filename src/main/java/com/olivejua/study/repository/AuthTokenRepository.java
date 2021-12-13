package com.olivejua.study.repository;

import com.olivejua.study.domain.auth.AuthToken;
import com.olivejua.study.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthTokenRepository extends JpaRepository<AuthToken, Long> {
    Optional<AuthToken> findByUser(User user);
}
