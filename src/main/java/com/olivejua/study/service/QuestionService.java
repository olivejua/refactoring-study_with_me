package com.olivejua.study.service;

<<<<<<< HEAD
import com.olivejua.study.repository.UserRepository;
import com.olivejua.study.repository.board.QuestionRepository;
import com.olivejua.study.web.dto.question.QuestionPostRequestDto;
=======
import com.olivejua.study.domain.User;
import com.olivejua.study.domain.board.Question;
import com.olivejua.study.repository.board.QuestionRepository;
import com.olivejua.study.web.dto.board.question.PostReadResponseDto;
import com.olivejua.study.web.dto.board.question.PostSaveRequestDto;
>>>>>>> eb338419de28de545ce7b0be7fde75c9bb377c78
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class QuestionService {

    private final QuestionRepository questionRepository;
<<<<<<< HEAD
    private final UserRepository userRepository;

//    public Long post(QuestionPostRequestDto requestDto, Long writerId) {
//        userRepository.findById(writerId)
//                .orElseThrow(new IllegalArgumentException())
//    }
=======

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
>>>>>>> eb338419de28de545ce7b0be7fde75c9bb377c78
}
