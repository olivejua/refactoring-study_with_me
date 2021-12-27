package com.olivejua.study.web.dto.post;

import com.olivejua.study.domain.post.Post;
import com.olivejua.study.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
@Getter
public abstract class PostReadResponseDto {
    private Long id;
    private AuthorResponseDto author;
    private String title;
    private LocalDateTime createdDate;

    public PostReadResponseDto(Post post) {
        id = post.getId();
        initAuthor(post.getAuthor());
        title = post.getTitle();
        createdDate = post.getCreatedDate();
    }

    public PostReadResponseDto(Long id, User author, String title, LocalDateTime createdDate) {
        this.id = id;
        initAuthor(author);
        this.title = title;
        this.createdDate = createdDate;
    }

    protected void initAuthor(User author) {
        this.author = new AuthorResponseDto(author);
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

    @NoArgsConstructor(access = PROTECTED)
    @Getter
    private static class CommentResponseDto {
        private Long id;
        private String content;
        private AuthorResponseDto author;
    }
}
