package com.olivejua.study.web.dto.board.study;

import lombok.Data;

@Data
public class StudySearchCondition {
    private SearchType searchType;
    private String keyword;
}
