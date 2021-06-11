package com.olivejua.study.service;

import com.olivejua.study.domain.User;
import com.olivejua.study.domain.board.Question;
import com.olivejua.study.repository.board.QuestionQueryRepository;
import com.olivejua.study.repository.board.QuestionRepository;
import com.olivejua.study.utils.ImageUploader;
import com.olivejua.study.web.dto.board.question.PostListResponseDto;
import com.olivejua.study.web.dto.board.question.PostReadResponseDto;
import com.olivejua.study.web.dto.board.question.PostSaveRequestDto;
import com.olivejua.study.web.dto.board.question.PostUpdateRequestDto;
import com.olivejua.study.web.dto.board.search.SearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final QuestionQueryRepository questionQueryRepository;
    private final ImageUploader imageUploader;
    private static final String BOARD_NAME = "question";

    @Transactional(readOnly = true)
    public Page<PostListResponseDto> list(Pageable pageable) {
        return questionQueryRepository.list(pageable);
    }

    @Transactional(readOnly = true)
    public Page<PostListResponseDto> search(SearchDto searchDto, Pageable pageable) {
        return questionQueryRepository.search(searchDto, pageable);
    }

    public Long post(PostSaveRequestDto requestDto, User writer) {
        Question newPost = Question.savePost(
                writer, requestDto.getTitle(), requestDto.getContent());

        questionRepository.save(newPost);
        imageUploader.uploadImagesIn(
                newPost.getContent(), BOARD_NAME, newPost.getId());

        return newPost.getId();
    }

    @Transactional(readOnly = true)
    public PostReadResponseDto read(Long postId, String servletPath) {
        Question entity = questionQueryRepository.findEntity(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = " + postId));

        imageUploader.readImagesIn(entity.getContent(), servletPath,  BOARD_NAME, postId);

        return new PostReadResponseDto(entity);
    }

    public Long update(Long postId, PostUpdateRequestDto requestDto) {
        Question post = findPost(postId);

        post.edit(requestDto.getTitle(), requestDto.getContent());
        imageUploader.uploadImagesIn(post.getContent(), BOARD_NAME, postId);

        return post.getId();
    }

    public Long delete(Long postId) {
        Question post = findPost(postId);

        questionRepository.delete(post);
        imageUploader.deleteImagesOf(BOARD_NAME, postId);

        return post.getId();
    }

    private Question findPost(Long postId) {
        return questionRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = " + postId));
    }
}
