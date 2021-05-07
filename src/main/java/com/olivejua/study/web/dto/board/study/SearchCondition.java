package com.olivejua.study.web.dto.board.study;

import lombok.Data;

@Data
public class SearchCondition {
    private SearchType searchType;
    private String keyword;
}
