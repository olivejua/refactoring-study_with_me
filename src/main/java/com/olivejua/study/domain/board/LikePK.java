package com.olivejua.study.domain.board;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
public class LikePK implements Serializable {

    private Long user;
    private Long boardPlaceRecommendation;
}
