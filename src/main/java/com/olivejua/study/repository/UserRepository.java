package com.olivejua.study.repository;

import com.olivejua.study.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u.name from User u")
    public List<String> findAllNames();

    public Optional<User> findByEmailAndSocialCode(String email, String socialCode);
}
