package com.olivejua.study.web.dto.board.place;

import com.olivejua.study.domain.User;
import com.olivejua.study.domain.board.LikeHistory;
import com.olivejua.study.domain.board.PlaceRecommendation;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PostListResponseDto {
    private Long postId;
    private String writerName;
    private String title;
    private String thumbnailPath;
    private int likeCount;
    private int dislikeCount;
    private int viewCount;
    private LocalDateTime createdDate;
    private int commentCount;


    public PostListResponseDto(PlaceRecommendation entity, long likeCount, long dislikeCount, long commentCount) {
        this.postId = entity.getId();
        this.writerName = entity.getWriter().getName();
        this.title = entity.getTitle();
        this.thumbnailPath = entity.getThumbnailPath();
        this.viewCount = entity.getViewCount();
        this.createdDate = entity.getCreatedDate();
        this.likeCount = (int) likeCount;
        this.dislikeCount = (int) dislikeCount;
        this.commentCount = (int) commentCount;
        updateLikeCount(entity.getLikes());
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

    private void updateLikeCount(List<LikeHistory> likes) {
        this.likeCount = (int) likes.stream()
                .filter(like -> like.isLike())
                .count();

        this.dislikeCount = (int) likes.stream()
                .filter(like -> !like.isLike())
                .count();
    }
}
