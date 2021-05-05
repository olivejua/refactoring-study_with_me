package com.olivejua.study.repository.board;

import com.olivejua.study.web.dto.board.SearchDto;
import com.olivejua.study.web.dto.board.study.PostListResponseDto;

import java.util.List;

public interface StudyRecruitmentRepositoryCustom {

    List<PostListResponseDto> list();

    List<PostListResponseDto> search(SearchDto searchDto);
}
