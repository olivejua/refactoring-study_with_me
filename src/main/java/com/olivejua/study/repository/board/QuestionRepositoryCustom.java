package com.olivejua.study.repository.board;

import com.olivejua.study.web.dto.board.question.PostListResponseDto;
import com.olivejua.study.web.dto.board.search.SearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionRepositoryCustom {

    Page<PostListResponseDto> list(Pageable pageable);

    Page<PostListResponseDto> search(SearchDto cond, Pageable pageable);
}
