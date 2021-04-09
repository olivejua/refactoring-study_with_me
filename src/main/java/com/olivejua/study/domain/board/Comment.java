package com.olivejua.study.domain.board;

import com.olivejua.study.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;
import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Comment {

    @Id @GeneratedValue
    @Column(name = "COMMENT_ID")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "POST_ID")
    private Board board;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "USER_ID")
    private User writer;

    @Column(name = "CONTENT")
    private String content;

    @OneToMany(mappedBy = "comment")
    private List<Reply> replies = new ArrayList<>();

    @Builder
    public Comment(Board board, User writer, String content) {
        this.board = board;
        this.writer = writer;
        this.content = content;
    }

    /**
     * 댓글 수정하기
     */
    public void edit(String content) {
        this.content = content;
    }
}
