package com.olivejua.study.domain;

import com.olivejua.study.domain.board.Board;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Reply extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REPLY_ID")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "USER_ID")
    private User writer;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "COMMENT_ID")
    private Comment comment;

    private String content;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "POST_ID")
    private Board board;

    private Reply(Comment comment, User writer, String content) {
        this.board = comment.getPost();
        this.comment = comment;
        this.writer = writer;
        this.content = content;
    }

    public static Reply createReply(Comment comment, User writer, String content) {
        Reply reply = new Reply(comment, writer, content);
        comment.getReplies().add(reply);

        return reply;
    }

    /**
     * 대댓글 수정하기
     */
    public void edit(String content) {
        this.content = content;
    }

    /**
     * 대댓글 삭제
     */
    public void delete() {
        comment.getReplies().remove(this);
    }
}
