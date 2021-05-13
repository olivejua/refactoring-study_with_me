package com.olivejua.study.web.dto.board.place.like;

import lombok.Getter;

@Getter
public class LikeUpdateRequestDto {
    Long postId;
    boolean isLike;
}
