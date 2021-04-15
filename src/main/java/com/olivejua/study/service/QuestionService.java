package com.olivejua.study.service;

import com.olivejua.study.repository.UserRepository;
import com.olivejua.study.repository.board.QuestionRepository;
import com.olivejua.study.web.dto.question.QuestionPostRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;

//    public Long post(QuestionPostRequestDto requestDto, Long writerId) {
//        userRepository.findById(writerId)
//                .orElseThrow(new IllegalArgumentException())
//    }
}
