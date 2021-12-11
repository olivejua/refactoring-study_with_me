package com.olivejua.study.repository;

import com.olivejua.study.domain.user.SocialCode;
import com.olivejua.study.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u.name from User u")
    List<String> findAllNames();

    Optional<User> findByName(String name);

    Optional<User> findByEmailAndSocialCode(String email, SocialCode socialCode);
}
