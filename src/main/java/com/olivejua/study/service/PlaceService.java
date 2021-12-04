package com.olivejua.study.service;

import com.olivejua.study.domain.User;
import com.olivejua.study.domain.Like;
import com.olivejua.study.domain.PlaceRecommendation;
import com.olivejua.study.repository.board.LinkRepository;
import com.olivejua.study.repository.board.PlaceRecommendationQueryRepository;
import com.olivejua.study.repository.board.PlaceRecommendationRepository;
import com.olivejua.study.utils.BoardImageUploader;
import com.olivejua.study.web.dto.board.place.PostListResponseDto;
import com.olivejua.study.web.dto.board.place.PostReadResponseDto;
import com.olivejua.study.web.dto.board.place.PostSaveRequestDto;
import com.olivejua.study.web.dto.board.search.SearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
@Transactional
public class PlaceService {

    private final PlaceRecommendationRepository placeRepository;
    private final PlaceRecommendationQueryRepository placeQueryRepository;
    private final LinkService linkService;
    private final LikeHistoryService likeHistoryService;
    private final BoardImageUploader boardImageUploader;
    private final CommentService commentService;

    public Page<PostListResponseDto> list(Pageable pageable) {
        return placeQueryRepository.findEntities(pageable);
    }

    public Page<PostListResponseDto> search(SearchDto cond, Pageable pageable) {
        return placeQueryRepository.findEntitiesWith(cond, pageable);
    }

    public Long post(PostSaveRequestDto requestDto, User writer) {
        PlaceRecommendation newPost = saveNewPost(requestDto, writer);

        return newPost.getId();
    }

    public Long post(PostSaveRequestDto requestDto, User writer, MultipartFile thumbnail) {
        PlaceRecommendation newPost = saveNewPost(requestDto, writer);
        boardImageUploader.uploadThumbnailInPlace(thumbnail, newPost);
        return newPost.getId();
    }

    private PlaceRecommendation saveNewPost(PostSaveRequestDto requestDto, User writer) {
        PlaceRecommendation newPost =
                PlaceRecommendation.createPost(writer, requestDto.getTitle(),
                        requestDto.getAddress(), requestDto.getAddressDetail(), requestDto.getThumbnailPath(),
                        requestDto.getContent(), requestDto.getLinks());

        placeRepository.save(newPost);
        linkService.update(newPost);
        boardImageUploader.uploadImagesInPlace(newPost);
        return newPost;
    }

    public PostReadResponseDto read(Long postId, User loginUser, String servletPath) {
        PlaceRecommendation entity = placeQueryRepository.findEntity(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + postId));

        Like like = placeQueryRepository.findLikeHistoryByPostAndUser(postId, loginUser.getId())
                .orElse(null);

        boardImageUploader.readImagesInPlace(entity, servletPath);

        return new PostReadResponseDto(entity, like);
    }

    public Long update(Long postId, PostSaveRequestDto requestDto) {
        PlaceRecommendation post = findPost(postId);

        post.update(requestDto.getTitle(), requestDto.getAddress(), requestDto.getAddressDetail(),
                requestDto.getThumbnailPath(), requestDto.getContent(), requestDto.getLinks());
        linkService.update(post);
        boardImageUploader.updateImagesInPlace(post);

        return post.getId();
    }

    public void delete(Long postId) {
        PlaceRecommendation post = findPost(postId);
        
        commentService.deleteByPost(post);
        likeHistoryService.deleteByPost(post);
        linkService.delete(post);
        placeRepository.delete(post);
        boardImageUploader.deleteImagesInPlace(postId);
    }

    private PlaceRecommendation findPost(Long postId) {
        return placeRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + postId));
    }
}

@RequiredArgsConstructor
@Transactional
@Service
class LinkService {
    private final LinkRepository linkRepository;

    public void update(PlaceRecommendation post) {
        delete(post);
        post.getLinks().forEach(linkRepository::save);
    }

    public void delete(PlaceRecommendation post) {
        linkRepository.deleteByPost(post);
    }
}
