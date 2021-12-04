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
@Getter
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

    /**
     * 글 수정
     */
    public void edit(String title, String content) {
        updateTitle(title);
        this.content = content;
    }
}
