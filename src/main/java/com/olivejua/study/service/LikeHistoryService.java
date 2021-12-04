package com.olivejua.study.service;

import com.olivejua.study.domain.User;
import com.olivejua.study.domain.Like;
import com.olivejua.study.domain.PlaceRecommendation;
import com.olivejua.study.repository.board.LikeHistoryRepository;
import com.olivejua.study.repository.board.PlaceRecommendationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LikeHistoryService {

    private final LikeHistoryRepository likeHistoryRepository;
    private final PlaceRecommendationRepository placeRepository;

    public void update(Long postId, User user, boolean isLike) {
        PlaceRecommendation post = findPost(postId);

//        findLikeHistory(post.getId(), user.getId())
//            .ifPresentOrElse(
//                    entity -> entity.update(isLike),
//                    () -> LikeHistory.saveLikeHistory(post, user, isLike));
    }

    public void delete(Long postId, Long userId) {
        Like entity = findLikeHistory(postId, userId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "해당 좋아요 기록이 없습니다. postId=" + postId + ", userId=" + userId));

        likeHistoryRepository.delete(entity);
    }

    public void deleteByPost(PlaceRecommendation post) {
        likeHistoryRepository.deleteLikeHistoriesByPost(post);
    }

    private Optional<Like> findLikeHistory(Long postId, Long userId) {
        return likeHistoryRepository.findOneByPostIdAndUserId(postId, userId);
    }

    private PlaceRecommendation findPost(Long postId) {
        return placeRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. postId=" + postId));
    }
}
