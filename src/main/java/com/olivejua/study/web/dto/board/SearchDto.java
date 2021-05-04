package com.olivejua.study.web.dto.board;

import lombok.Getter;

@Getter
public class SearchDto {
    private String searchType;
    private String keyword;

    public SearchDto(String searchType, String keyword) {
        this.searchType = searchType;
        this.keyword = keyword;
    }
}
