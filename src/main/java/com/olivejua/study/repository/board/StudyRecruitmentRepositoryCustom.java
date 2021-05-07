package com.olivejua.study.repository.board;

import com.olivejua.study.web.dto.board.SearchDto;
import com.olivejua.study.web.dto.board.study.PostListResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StudyRecruitmentRepositoryCustom {

    Page<PostListResponseDto> list(Pageable pageable);

    Page<PostListResponseDto> search(SearchDto searchDto, Pageable pageable);
}
