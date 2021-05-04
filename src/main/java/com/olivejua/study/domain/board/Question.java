package com.olivejua.study.domain.board;

import com.olivejua.study.domain.Comment;
import com.olivejua.study.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Entity
@DiscriminatorValue("question")
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Question extends Board {

    @Column(nullable = false)
    private String content;

    @OneToMany(mappedBy = "post")
    private List<Comment> comment = new ArrayList<>();

    /**
     * 글 등록
     */
    public static Question savePost(User writer, String title, String content) {
        Question newPost = new Question();

        newPost.createPost(writer, title);
        newPost.content = content;

        return newPost;
    }

    /**
     * 글 수정
     */
    public void edit(String title, String content) {
        editTitle(title);
        this.content = content;
    }
}
