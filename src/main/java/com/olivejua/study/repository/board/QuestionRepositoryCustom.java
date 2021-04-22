package com.olivejua.study.repository.board;

import com.olivejua.study.domain.board.Question;

import java.util.List;

public interface QuestionRepositoryCustom {
    List<Question> findByTitle(String title);
}
