package com.olivejua.study.service;

import com.olivejua.study.domain.User;
import com.olivejua.study.domain.board.PlaceRecommendation;
import com.olivejua.study.repository.board.LinkRepository;
import com.olivejua.study.repository.board.PlaceRecommendationRepository;
import com.olivejua.study.web.dto.board.place.PostReadResponseDto;
import com.olivejua.study.web.dto.board.place.PostSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class PlaceService {

    private final PlaceRecommendationRepository placeRepository;
    private final LinkService linkService;

    public Long post(PostSaveRequestDto requestDto, User writer) {
        PlaceRecommendation newPost =
                PlaceRecommendation.savePost(writer, requestDto.getTitle(),
                        requestDto.getAddress(), requestDto.getAddressDetail(), requestDto.getThumbnailPath(),
                        requestDto.getContent(),requestDto.getLinks());

        placeRepository.save(newPost);
        linkService.update(newPost);

        return newPost.getId();
    }

    public Long update(Long postId, PostSaveRequestDto requestDto) {
        PlaceRecommendation post = findPost(postId);

        post.edit(requestDto.getTitle(), requestDto.getAddress(), requestDto.getAddressDetail(),
                requestDto.getThumbnailPath(), requestDto.getContent(), requestDto.getLinks());
        linkService.update(post);

        return post.getId();
    }

    public void delete(Long postId) {
        PlaceRecommendation post = findPost(postId);

        linkService.delete(post);
        placeRepository.delete(post);
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