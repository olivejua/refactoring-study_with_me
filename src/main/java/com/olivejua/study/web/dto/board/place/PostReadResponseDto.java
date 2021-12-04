package com.olivejua.study.web.dto.board.place;

import com.olivejua.study.domain.Comment;
import com.olivejua.study.domain.Like;
import com.olivejua.study.domain.Link;
import com.olivejua.study.domain.PlaceRecommendation;
import com.olivejua.study.web.dto.PageDto;
import com.olivejua.study.web.dto.board.place.like.LikeStatus;
import com.olivejua.study.web.dto.comment.CommentReadResponseDto;
import com.olivejua.study.web.dto.user.WriterReadDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
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
    private PageDto pageInfo;

    public PostReadResponseDto(PlaceRecommendation entity, Like userLikeStatus) {
        this.postId = entity.getId();
        this.title = entity.getTitle();
        this.writer = new WriterReadDto(entity.getAuthor());
        this.address = entity.getAddress();
        this.addressDetail = entity.getAddressDetail();
        this.thumbnailPath = entity.getThumbnailName();
        this.content = entity.getContent();
        this.links = changeLinkEntityToStrings(entity.getLinks());
        this.viewCount = entity.getViewCount();
        this.createDate = entity.getCreatedDate();

        updateLikeCounts(entity.getLikes());
        this.comments = changeCommentsToDtos(entity.getComment());

        likeStatus = getLikeStatusOfLoginUser(userLikeStatus);
    }

    private List<String> changeLinkEntityToStrings(List<Link> linkEntities) {
        return linkEntities.stream()
                .map(Link::getElement)
                .collect(Collectors.toList());
    }

    private void updateLikeCounts(List<Like> likes) {
        if (likes == null) return;

        this.likeCount = (int) likes.stream()
                .filter(Like::isLike).count();
        this.dislikeCount = (int) likes.stream()
                .filter(e -> !e.isLike()).count();
    }

    private List<CommentReadResponseDto> changeCommentsToDtos(List<Comment> comments) {
        return comments.stream()
                .map(CommentReadResponseDto::new)
                .collect(Collectors.toList());
    }

    private LikeStatus getLikeStatusOfLoginUser(Like like) {
        if (like == null) {
            return LikeStatus.NOT_YET;
        }

        return like.isLike() ? LikeStatus.LIKE : LikeStatus.DISLIKE;
    }

    public void savePageInfo(PageDto pageInfo) {
        this.pageInfo = pageInfo;
    }
}
