package com.olivejua.study.integration.controller;

import com.olivejua.study.utils.PostImagePaths;
import com.olivejua.study.web.dto.placeRecommendation.PlaceRecommendationSaveRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;

import static com.olivejua.study.utils.ApiUrlPaths.PLACES_RECOMMENDATION;
import static com.olivejua.study.utils.ApiUrlPaths.POSTS;
import static org.apache.http.HttpHeaders.AUTHORIZATION;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PlaceRecommendationControllerTest extends CommonControllerTest {
    private static final String DIRECT = "place-recommendation/";

    @BeforeEach
    void setup() {
        setupUserAndAccessToken();
    }

    @Test
    @DisplayName("스터디 모집 게시글을 첨부이미와 같이 작성한다")
    void testSaveContainingImages() throws Exception {
        PlaceRecommendationSaveRequestDto requestDto = placeRecommendationFactory.saveRequestDto();

        String baseImageUrl = "https://aws.s3.simplelog.com/" + PostImagePaths.PLACE_RECOMMENDATION + "1/";
        when(uploadService.upload(anyList(), anyString())).thenReturn(List.of(baseImageUrl+"1.jpg", baseImageUrl+"2.jpg"));

        mockMvc.perform(multipart(PLACES_RECOMMENDATION + POSTS)
                        .file("images", getMockImage().getBytes())
                        .file("images", getMockImage().getBytes())
                        .params(toParamsMap(requestDto))
                        .header(AUTHORIZATION, accessToken)
                        .accept(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto))
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("success").value(true))
        ;
    }

    private MultiValueMap<String, String> toParamsMap(PlaceRecommendationSaveRequestDto requestDto) {
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("title", requestDto.getTitle());
        requestDto.getLinks()
                .forEach(link -> params.add("links", link));
        params.add("address", requestDto.getAddress());
        params.add("addressDetail", requestDto.getAddressDetail());
        params.add("content", requestDto.getContent());

        return params;
    }
}
