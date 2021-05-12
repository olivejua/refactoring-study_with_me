package com.olivejua.study.web.dto.board.search;

import lombok.Getter;

@Getter
public class SearchDto {
    private SearchType searchType;
    private String keyword;

    public SearchDto(String searchType, String keyword) {
        this.searchType = SearchType.valueOf(searchType);
        this.keyword = keyword;
    }
}