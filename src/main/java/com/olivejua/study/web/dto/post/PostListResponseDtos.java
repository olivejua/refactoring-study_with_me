package com.olivejua.study.web.dto.post;

import com.olivejua.study.response.ListResult;
import com.olivejua.study.response.PageInfo;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class PostListResponseDtos<T>  {
    private final List<T> posts = new ArrayList<>();
    private PageInfo pageInfo;

    public PostListResponseDtos(List<T> posts, PageInfo pageInfo) {
        this.posts.addAll(posts);
        this.pageInfo = pageInfo;
    }

    public ListResult<T> toListResult() {
        return new ListResult<>(posts, pageInfo);
    }
}
