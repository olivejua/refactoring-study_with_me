package com.olivejua.study.repository.board;

import com.olivejua.study.domain.Like;
import com.olivejua.study.domain.PlaceRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LikeHistoryRepository extends JpaRepository<Like, Like.LikePK> {

    @Query("SELECT lh FROM Like AS lh " +
            "WHERE lh.post.id=:postId and lh.user.id=:userId")
    Optional<Like> findOneByPostIdAndUserId(Long postId, Long userId);

    public void deleteLikeHistoriesByPost(PlaceRecommendation post);
}
