package com.olivejua.study.repository.board;

import com.olivejua.study.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Post, Long> {
}
