package com.olivejua.study.web.dto.board.place;

import com.olivejua.study.domain.User;
import com.olivejua.study.domain.board.Link;
import com.olivejua.study.domain.board.PlaceRecommendation;
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

    public PostReadResponseDto(Long postId, String title, User writer,
                               String address, String addressDetail, String thumbnailPath,
                               String content, List<Link> links, int likeCount,
                               int dislikeCount, int viewCount, LocalDateTime createDate) {
        this.postId = postId;
        this.title = title;
        this.writer = new WriterReadDto(writer);
        this.address = address;
        this.addressDetail = addressDetail;
        this.thumbnailPath = thumbnailPath;
        this.content = content;
        this.links = links.stream()
                .map(Link::getElement)
                .collect(Collectors.toList());

        this.likeCount = likeCount;
        this.dislikeCount = dislikeCount;
        this.viewCount = viewCount;
        this.createDate = createDate;
    }

    private enum LikeStatus {
        LIKE,
        DISLIKE,
        NOT_YET
    }
}
