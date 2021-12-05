package com.olivejua.study.repository;

import com.olivejua.study.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Post, Long> {
}
