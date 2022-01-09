package com.olivejua.study.integration.service;

import com.olivejua.study.auth.dto.LoginUser;
import com.olivejua.study.domain.placeRecommendation.PlaceRecommendation;
import com.olivejua.study.domain.user.User;
import com.olivejua.study.integration.IntegrationTest;
import com.olivejua.study.repository.PlaceRecommendationRepository;
import com.olivejua.study.service.placeRecommendation.PlaceRecommendationService;
import com.olivejua.study.utils.PostImagePaths;
import com.olivejua.study.web.dto.placeRecommendation.PlaceRecommendationSaveRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlaceRecommendationServiceTest extends IntegrationTest {

    @Autowired
    private PlaceRecommendationService placeRecommendationService;

    @Autowired
    private PlaceRecommendationRepository placeRecommendationRepository;

    private User author;
    private LoginUser loginUser;
    private final String MOCK_DOMAIN_URL = "https://aws.s3.simplelog.com/";
    private final String POST_IMAGE_PATH = PostImagePaths.PLACE_RECOMMENDATION + "1/";

    @BeforeEach
    void setup() {
        author = userFactory.user();
        loginUser = new LoginUser(author);
    }

    @Test
    @DisplayName("게시글을 작성한다.")
    void testSavePost() {
        //given
        PlaceRecommendationSaveRequestDto requestDto = placeRecommendationFactory.saveRequestDto();

        //when
        Long savedPostId = placeRecommendationService.savePost(requestDto, loginUser.getUser());

        //post
        Optional<PlaceRecommendation> optionalFindPost = placeRecommendationRepository.findById(savedPostId);
        assertTrue(optionalFindPost.isPresent());

        PlaceRecommendation findPost = optionalFindPost.get();
        assertEquals(requestDto.getTitle(), findPost.getTitle());
    }
}
