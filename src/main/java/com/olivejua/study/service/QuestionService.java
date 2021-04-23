package com.olivejua.study.service;

import com.olivejua.study.domain.User;
import com.olivejua.study.domain.board.Question;
import com.olivejua.study.repository.board.QuestionQueryRepository;
import com.olivejua.study.repository.board.QuestionRepository;
import com.olivejua.study.web.dto.board.question.PostReadResponseDto;
import com.olivejua.study.web.dto.board.question.PostSaveRequestDto;
import com.olivejua.study.web.dto.question.PostUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class QuestionService {

    private final QuestionQueryRepository questionQueryRepository;
    private final QuestionRepository questionRepository;

    public Long post(PostSaveRequestDto requestDto, User writer) {
        Question newPost = Question.savePost(
                writer, requestDto.getTitle(), requestDto.getContent());

        questionRepository.save(newPost);
        return newPost.getId();
    }

    @Transactional(readOnly = true)
    public PostReadResponseDto findPostBy(Long postId) {
        Question findPost = questionQueryRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = " + postId));

        return new PostReadResponseDto(findPost);
    }

    public Question update(Long postId, PostUpdateRequestDto requestDto) {
        Question post = questionRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = " + postId));
        System.out.println("***** update 함수에서 찾아온 post = " + post);

        post.edit(requestDto.getTitle(), requestDto.getContent());

        return post;
    }

    public Long delete(Long postId) {
        Question post = questionRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = " + postId));

        questionRepository.delete(post);

        return post.getId();
    }
}
