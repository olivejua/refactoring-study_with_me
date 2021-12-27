package com.olivejua.study.web.dto.post;

import com.olivejua.study.response.PageInfo;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

@Getter
public class PostListResponseDto<T>  {
    private final List<T> posts = new ArrayList<>();
    private PageInfo pageInfo;

    public PostListResponseDto(List<T> posts, PageInfo pageInfo) {
        this.posts.addAll(posts);
        this.pageInfo = pageInfo;
    }

    private PageInfo toPageInfo(Page<T> posts) {
        return PageInfo.builder()
                .totalElements(posts.getTotalElements())
                .totalPages(posts.getTotalPages())
                .number(posts.getNumber())
                .first(posts.isFirst())
                .last(posts.isLast())
                .numberOfElements(posts.getNumberOfElements())
                .build();
    }
}
