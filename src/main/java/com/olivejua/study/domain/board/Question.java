package com.olivejua.study.domain.board;

import com.olivejua.study.domain.Comment;
import com.olivejua.study.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    private String content;

    @OneToMany(mappedBy = "board")
    private List<Comment> comment = new ArrayList<>();

    @Builder
    public Question(User writer, String title, String content) {
        createPost(writer, title);
        this.content = content;
    }

    public void edit(String title, String content) {
        editTitle(title);
        this.content = content;
    }
}
