package com.olivejua.study.web.dto.placeRecommendation;

import com.olivejua.study.domain.placeRecommendation.PlaceRecommendation;
import com.olivejua.study.web.dto.post.PostListResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
@Getter
public class PlaceRecommendationListResponseDto extends PostListResponseDto {

    public PlaceRecommendationListResponseDto(PlaceRecommendation post) {
        super(post);
    }
}
