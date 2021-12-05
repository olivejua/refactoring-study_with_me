package com.olivejua.study.domain.comment;

import com.olivejua.study.domain.BaseTimeEntity;
import com.olivejua.study.domain.post.Post;
import com.olivejua.study.domain.reply.Replies;
import com.olivejua.study.domain.reply.Reply;
import com.olivejua.study.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Comment extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMMENT_ID")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "POST_ID")
    private Post post;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "USER_ID")
    private User author;

    private String content;

    private final Replies replies = new Replies();

    private Comment(Post post, User author, String content) {
        this.post = post;
        this.author = author;
        this.content = content;
    }

    /**
     * 댓글 달기
     */
    public static Comment createComment(Post post, User writer, String content) {
        Comment comment = new Comment(post, writer, content);
        post.addComment(comment);

        return comment;
    }

    /**
     * 댓글 수정하기
     */
    public void update(String content) {
        this.content = content;
    }

    public void removeReply(Reply reply) {
        replies.remove(reply);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return Objects.equals(id, comment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
