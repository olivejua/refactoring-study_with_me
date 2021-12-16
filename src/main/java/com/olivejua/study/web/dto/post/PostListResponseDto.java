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

    public PostListResponseDto(Page<T> posts) {
        this.posts.addAll(posts.getContent());
        this.pageInfo = toPageInfo(posts);
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
