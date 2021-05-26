package com.olivejua.study.web.dto.board.place;

import com.olivejua.study.domain.User;
import com.olivejua.study.domain.board.LikeHistory;
import com.olivejua.study.domain.board.Link;
import com.olivejua.study.domain.board.PlaceRecommendation;
import com.olivejua.study.web.dto.comment.CommentReadResponseDto;
import com.olivejua.study.web.dto.user.WriterReadDto;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PostReadResponseDto {

    private Long postId;
    private String title;
    private WriterReadDto writer;
    private String address;
    private String addressDetail;
    private String thumbnailPath;
    private String content;
    private List<String> links;
    private int likeCount;
    private int dislikeCount;
    private int viewCount;
    private LikeStatus likeStatus;
    private LocalDateTime createDate;
    private List<CommentReadResponseDto> comments;


    public PostReadResponseDto(PlaceRecommendation entity) {
        this.postId = entity.getId();
        this.title = entity.getTitle();
        this.writer = new WriterReadDto(entity.getWriter());
        this.address = entity.getAddress();
        this.addressDetail = entity.getAddressDetail();
        this.thumbnailPath = entity.getThumbnailPath();
        this.content = entity.getContent();
        this.links = entity.getLinks().stream()
                .map(Link::getElement)
                .collect(Collectors.toList());

        this.likeCount = (int) entity.getLikes().stream()
                .filter(LikeHistory::isLike).count();
        this.dislikeCount = (int) entity.getLikes().stream()
                .filter(e -> !e.isLike()).count();
        this.viewCount = entity.getViewCount();
        this.createDate = entity.getCreatedDate();
        this.comments = entity.getComment().stream()
                .map(CommentReadResponseDto::new)
                .collect(Collectors.toList());
    }

    private enum LikeStatus {
        LIKE,
        DISLIKE,
        NOT_YET
    }
}
