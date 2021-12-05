package com.olivejua.study.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import static lombok.AccessLevel.PROTECTED;

@Entity
@DiscriminatorValue("question")
@NoArgsConstructor(access = PROTECTED)
public class Question extends Post {

    @Column(nullable = false)
    private String content;

    public Question(User author, String title, String content) {
        super(author, title);
        this.content = content;
    }

    /**
     * 글 등록
     */
    public static Question createPost(User author, String title, String content) {
        return new Question(author, title, content);
    }

    public static Question createPost(Long id, User author, String title, String content) {
        Question post = createPost(author, title, content);
        post.id = id;

        return post;
    }

    /**
     * 글 수정
     */
    public void update(String title, String content) {
        updateTitle(title);
        this.content = content;
    }

    /**
     * Getter
     */
    public String getContent() {
        return content;
    }
}
