package com.olivejua.study.web.dto.post;

import com.olivejua.study.domain.post.Post;
import com.olivejua.study.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
@Getter
public abstract class PostListResponseDto {
    protected Long id;
    protected AuthorResponseDto author;
    protected String title;
    protected LocalDateTime createdDate;

    public PostListResponseDto(Post post) {
        id = post.getId();
        initAuthor(post.getAuthor());
        title = post.getTitle();
        createdDate = post.getCreatedDate();
    }

    protected void initAuthor(User user) {
        this.author = new AuthorResponseDto(user);
    }

    @NoArgsConstructor(access = PROTECTED)
    @Getter
    private static class AuthorResponseDto {
        private Long id;
        private String name;

        public AuthorResponseDto(User user) {
            id = user.getId();
            name = user.getName();
        }
    }
}
