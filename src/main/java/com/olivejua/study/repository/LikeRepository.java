package com.olivejua.study.repository;

import com.olivejua.study.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Like.LikePK> {
}
