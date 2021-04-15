package com.olivejua.study.repository;

import com.olivejua.study.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u.name from User u")
    public List<String> findAllNames();
}
