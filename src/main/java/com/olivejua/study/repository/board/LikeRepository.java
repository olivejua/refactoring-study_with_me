package com.olivejua.study.repository.board;

import com.olivejua.study.domain.board.Like;
import com.olivejua.study.domain.board.LikePK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, LikePK> {

}
