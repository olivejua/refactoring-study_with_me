package com.olivejua.study.web;

import com.olivejua.study.auth.annotation.AppLoginUser;
import com.olivejua.study.auth.dto.LoginUser;
import com.olivejua.study.exception.validation.RequestDtoValidationException;
import com.olivejua.study.response.ListResult;
import com.olivejua.study.response.SingleResult;
import com.olivejua.study.response.SuccessResult;
import com.olivejua.study.service.placeRecommendation.PlaceRecommendationService;
import com.olivejua.study.web.dto.placeRecommendation.PlaceRecommendationListResponseDto;
import com.olivejua.study.web.dto.placeRecommendation.PlaceRecommendationSaveRequestDto;
import com.olivejua.study.web.dto.placeRecommendation.PlaceRecommendationUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static com.olivejua.study.utils.ApiUrlPaths.*;

@RequiredArgsConstructor
@RequestMapping(PLACES_RECOMMENDATION)
@RestController
public class PlaceRecommendationController {
    private final PlaceRecommendationService placeRecommendationService;

    @GetMapping(POSTS)
    public ResponseEntity<ListResult<PlaceRecommendationListResponseDto>> getPosts(Pageable pageRequest) {

        return ResponseEntity.ok(null);
    }

    @GetMapping(POSTS + VAR_POST_ID)
    public ResponseEntity<SingleResult> getPost(@PathVariable Long postId) {

        return ResponseEntity.ok(null);
    }

    @PostMapping(POSTS)
    public ResponseEntity<SuccessResult> savePost(@AppLoginUser LoginUser loginUser,
                                                  @Validated PlaceRecommendationSaveRequestDto requestDto, Errors errors) {
        validateErrors(errors);

        Long savedPostId = placeRecommendationService.savePost(requestDto, loginUser.getUser());

        return ResponseEntity.created(URI.create(PLACES_RECOMMENDATION+POSTS+"/"+savedPostId))
                .body(SuccessResult.createSuccessResult());
    }

    @PutMapping(POSTS + VAR_POST_ID)
    public ResponseEntity<SuccessResult> updatePost(@PathVariable Long postId,
                                                    @Validated PlaceRecommendationUpdateRequestDto requestDto, Errors errors,
                                                    @AppLoginUser LoginUser loginUser) {
        validateErrors(errors);

        placeRecommendationService.updatePost(postId, requestDto, loginUser.getUser());
        return ResponseEntity.ok(SuccessResult.createSuccessResult());
    }
    @DeleteMapping(POSTS + VAR_POST_ID)
    public ResponseEntity<SuccessResult> deletePost(@PathVariable Long postId,
                                                    @AppLoginUser LoginUser loginUser) {
        placeRecommendationService.deletePost(postId, loginUser.getUser());
        return ResponseEntity.ok(SuccessResult.createSuccessResult());
    }

    private void validateErrors(Errors errors) {
        if (errors.hasErrors()) {
            throw new RequestDtoValidationException(errors);
        }
    }
}
