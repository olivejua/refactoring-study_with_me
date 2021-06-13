package com.olivejua.study.repository.board;

import com.olivejua.study.domain.board.LikeHistory;
import com.olivejua.study.domain.board.LikeHistoryPK;
import com.olivejua.study.domain.board.PlaceRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LikeHistoryRepository extends JpaRepository<LikeHistory, LikeHistoryPK> {

    @Query("SELECT lh FROM LikeHistory AS lh " +
            "WHERE lh.post.id=:postId and lh.user.id=:userId")
    Optional<LikeHistory> findOneByPostIdAndUserId(Long postId, Long userId);

    public void deleteLikeHistoriesByPost(PlaceRecommendation post);
}
