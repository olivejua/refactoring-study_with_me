package com.olivejua.study.web.dto.board.place;

import com.olivejua.study.domain.board.LikeHistory;
import com.olivejua.study.domain.board.PlaceRecommendation;

import java.time.LocalDateTime;
import java.util.List;

public class PostListResponseDto {
    private Long postId;
    private String writerName;
    private String title;
    private String thumbnailPath;
    private int likeCount;
    private int dislikeCount;
    private int viewCount;
    private LocalDateTime createdDate;


    public PostListResponseDto(PlaceRecommendation entity) {
        this.postId = entity.getId();
        this.writerName = entity.getWriter().getName();
        this.title = entity.getTitle();
        this.thumbnailPath = entity.getThumbnailPath();
        this.likeCount = getLikeCount(entity.getLikes(), true);
        this.dislikeCount = getLikeCount(entity.getLikes(), false);
        this.viewCount = entity.getViewCount();
        this.createdDate = entity.getCreatedDate();
    }

    public PostListResponseDto(Long postId, String writerName, String title,
                               String thumbnailPath, int likeCount, int dislikeCount,
                               int viewCount, LocalDateTime createdDate) {
        this.postId = postId;
        this.writerName = writerName;
        this.title = title;
        this.thumbnailPath = thumbnailPath;
        this.likeCount = likeCount;
        this.dislikeCount = dislikeCount;
        this.viewCount = viewCount;
        this.createdDate = createdDate;
    }

    private int getLikeCount(List<LikeHistory> likes, boolean isLike) {
        return (int) likes.stream()
                .filter(like -> like.isLike()==isLike)
                .count();
    }
}
