package com.olivejua.study.service.placeRecommendation;

import com.olivejua.study.domain.user.User;
import com.olivejua.study.web.dto.placeRecommendation.PlaceRecommendationListResponseDto;
import com.olivejua.study.web.dto.placeRecommendation.PlaceRecommendationReadResponseDto;
import com.olivejua.study.web.dto.placeRecommendation.PlaceRecommendationSaveRequestDto;
import com.olivejua.study.web.dto.placeRecommendation.PlaceRecommendationUpdateRequestDto;
import com.olivejua.study.web.dto.post.PostListResponseDtos;
import org.springframework.data.domain.Pageable;

public interface PlaceRecommendationService {

    Long savePost(PlaceRecommendationSaveRequestDto requestDto, User author);

    void updatePost(Long postId, PlaceRecommendationUpdateRequestDto requestDto, User author);

    void deletePost(Long postId, User author);

    PlaceRecommendationReadResponseDto getOnePost(Long postId);

    PostListResponseDtos<PlaceRecommendationListResponseDto> getPosts(Pageable pageable);
}
