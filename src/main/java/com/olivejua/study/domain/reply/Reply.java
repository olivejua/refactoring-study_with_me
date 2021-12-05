package com.olivejua.study.domain.reply;

import com.olivejua.study.domain.BaseTimeEntity;
import com.olivejua.study.domain.comment.Comment;
import com.olivejua.study.domain.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

import static javax.persistence.FetchType.LAZY;

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

    private Reply(Comment comment, User writer, String content) {
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
    public void update(String content) {
        this.content = content;
    }

    /**
     * 대댓글 삭제
     */
    public void delete() {
        comment.getReplies().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reply reply = (Reply) o;
        return Objects.equals(id, reply.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
