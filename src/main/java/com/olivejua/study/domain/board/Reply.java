package com.olivejua.study.domain.board;

import com.olivejua.study.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Reply {

    @Id @GeneratedValue
    @Column(name = "REPLY_ID")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "USER_ID")
    private User writer;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "REPLY_ID")
    private Comment comment;

    @Column(name = "CONTENT")
    private String content;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "POST_ID")
    private Board board;

    public Reply(Long id, User writer, Comment comment, String content, Board board) {
        this.id = id;
        this.writer = writer;
        this.comment = comment;
        this.content = content;
        this.board = board;
    }

    /**
     * 대댓글 수정하기
     */
    public void edit(String content) {
        this.content = content;
    }
}
