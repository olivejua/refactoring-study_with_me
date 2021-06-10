package com.olivejua.study.web.dto;

import com.olivejua.study.web.dto.board.search.SearchDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PageDto {
    private int page;
    private SearchDto search;

    public PageDto(int page, String searchType, String keyword) {
        this.page = page;
        this.search = new SearchDto(searchType, keyword);
    }
}
