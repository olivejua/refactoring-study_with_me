package com.olivejua.study.service.placeRecommendation;

import com.olivejua.study.domain.placeRecommendation.PlaceRecommendation;
import com.olivejua.study.domain.user.User;
import com.olivejua.study.exception.post.NotFoundPostException;
import com.olivejua.study.repository.PlaceRecommendationRepository;
import com.olivejua.study.service.post.PostService;
import com.olivejua.study.web.dto.placeRecommendation.PlaceRecommendationListResponseDto;
import com.olivejua.study.web.dto.placeRecommendation.PlaceRecommendationReadResponseDto;
import com.olivejua.study.web.dto.placeRecommendation.PlaceRecommendationSaveRequestDto;
import com.olivejua.study.web.dto.placeRecommendation.PlaceRecommendationUpdateRequestDto;
import com.olivejua.study.web.dto.post.PostListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.olivejua.study.utils.PostImagePaths.PLACE_RECOMMENDATION;

@RequiredArgsConstructor
@Transactional
@Service
public class DefaultPlaceRecommendationService implements PlaceRecommendationService {
    private final PlaceRecommendationRepository placeRecommendationRepository;
    private final PostService postService;

    @Override
    public Long savePost(PlaceRecommendationSaveRequestDto requestDto, User author) {
        PlaceRecommendation savedPost = placeRecommendationRepository.save(requestDto.toEntity(author));

        List<String> uploadedImageUrls = postService.uploadImages(requestDto.getImages(), PLACE_RECOMMENDATION, savedPost.getId());
        savedPost.addImages(uploadedImageUrls);

        return savedPost.getId();
    }

    @Override
    public void updatePost(Long postId, PlaceRecommendationUpdateRequestDto requestDto, User author) {
        PlaceRecommendation post = findPostById(postId);
        postService.validateAuthor(post, author);

        post.update(requestDto.getTitle(),
                requestDto.getAddress(),
                requestDto.getAddressDetail(),
                requestDto.getContent(),
                requestDto.getLinks());

        List<String> uploadedImageUrls = postService.replaceImages(requestDto.getImages(), PLACE_RECOMMENDATION, post);
        post.replaceImages(uploadedImageUrls);
    }

    @Override
    public void deletePost(Long postId, User author) {
        PlaceRecommendation post = findPostById(postId);
        postService.validateAuthor(post, author);
        postService.removeImages(post);

        placeRecommendationRepository.delete(post);
    }

    @Override
    public PlaceRecommendationReadResponseDto getOnePost(Long postId) {
        PlaceRecommendation findPost = findPostById(postId);
        findPost.addViewCount();

        return new PlaceRecommendationReadResponseDto(findPost);
    }

    @Override
    public PostListResponseDto<PlaceRecommendationListResponseDto> getPosts(Pageable pageable) {
        placeRecommendationRepository.findPosts(pageable);
    }

    private PlaceRecommendation findPostById(Long postId) {
        return placeRecommendationRepository.findById(postId)
                .orElseThrow(() -> new NotFoundPostException(postId));
    }

}
