package com.olivejua.study.service;

import com.olivejua.study.domain.User;
import com.olivejua.study.domain.board.Question;
import com.olivejua.study.repository.board.QuestionRepository;
import com.olivejua.study.web.dto.board.SearchDto;
import com.olivejua.study.web.dto.board.question.PostListResponseDto;
import com.olivejua.study.web.dto.board.question.PostReadResponseDto;
import com.olivejua.study.web.dto.board.question.PostSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
@Transactional
public class QuestionService {

    private final QuestionRepository questionRepository;

    public Long post(PostSaveRequestDto requestDto, User writer) {
        Question newPost = Question.createPost(
                writer, requestDto.getTitle(), requestDto.getContent());

        questionRepository.save(newPost);
        return newPost.getId();
    }

    @Transactional(readOnly = true)
    public PostReadResponseDto findPostBy(Long postId) {
        Question findPost = questionRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = " + postId));

        return new PostReadResponseDto(findPost);
    }
//
//    public PostListResponseDto searchPosts(Pageable pageable, SearchDto searchDto) {
//        switch (searchDto.getSearchType()) {
//            case "title" :
//                results = questionRepository.findByTitleContainingOrContentContaining(
//                        searchDto.getKeyword(), searchDto.getKeyword(), pageable);
//                break;
//            case "writer" :
//                User user = User.builder().nickname(keyword).build();
//                results = questionRepository.findByUserContaining(user, pageable);
//                break;
//            default:
//                results = questionRepository.findAll(pageable);
//                break;
//        }
//    }
}
