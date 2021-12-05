package com.olivejua.study.domain.like;

import com.olivejua.study.domain.post.Post;
import com.olivejua.study.domain.user.User;
import lombok.Getter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import static com.olivejua.study.domain.like.LikeStatus.*;
import static javax.persistence.EnumType.*;

@Getter
@Table(name = "LIKES")
@Entity
public class Like {

    @Embeddable
    public static class LikePK implements Serializable {
        @Column(name = "USER_ID")
        private Long userId;

        @Column(name = "POST_ID")
        private Long postId;

        public LikePK() {}

        public LikePK(Long userId, Long postId) {
            this.userId = userId;
            this.postId = postId;
        }
    }

    @EmbeddedId
    private LikePK likePK = new LikePK();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", insertable = false, updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID", insertable = false, updatable = false)
    private Post post;

    @Enumerated(STRING)
    private LikeStatus status;

    public static Like createLike(Post post, User user, LikeStatus status) {
        Like like = new Like();
        like.post = post;
        like.user = user;
        like.status = status;

        return like;
    }

    /**
     * 좋아요 수정
     */
    public void update(LikeStatus status) {
        this.status = status;
    }

    /**
     * Status
     */
    public boolean hasStatusOfLike() {
        return status==LIKE;
    }

    public boolean hasStatusOfDislike() {
        return status==DISLIKE;
    }

    /**
     * equals and hashcode
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Like that = (Like) o;
        return that.getPost().equals(this.post) &&
                that.getUser().equals(this.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUser(), getPost(), status);
    }
}
