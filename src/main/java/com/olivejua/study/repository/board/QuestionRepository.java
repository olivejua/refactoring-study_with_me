package com.olivejua.study.repository.board;

import com.olivejua.study.domain.board.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long>, QuestionRepositoryCustom {
    Page<Question> findByTitleContainingOrContentContaining(String title, String content, Pageable pageable);
}
