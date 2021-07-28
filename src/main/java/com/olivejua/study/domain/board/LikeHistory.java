package com.olivejua.study.domain.board;

import com.olivejua.study.domain.User;
import lombok.Getter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Getter
//@IdClass(LikeHistoryPK.class)
@Entity
public class LikeHistory {

    @Embeddable
    public static class LikeHistoryPK implements Serializable {
        @Column(name = "USER_ID")
        private Long userId;

        @Column(name = "POST_ID")
        private Long postId;

        public LikeHistoryPK() {}

        public LikeHistoryPK(Long userId, Long postId) {
            this.userId = userId;
            this.postId = postId;
        }
    }

    @EmbeddedId
    private LikeHistoryPK likeHistoryPK = new LikeHistoryPK();

//    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", insertable = false, updatable = false)
    private User user;

//    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID", insertable = false, updatable = false)
    private PlaceRecommendation post;

    @Column(nullable = false, columnDefinition = "TINYINT(1)", name = "IS_LIKE")
    private boolean isLike;

    public static LikeHistory createLikeHistory(PlaceRecommendation post, User user, boolean isLike) {
        LikeHistory like = new LikeHistory();
        like.post = post;
        like.user = user;
        like.isLike = isLike;

        post.getLikes().add(like);

        return like;
    }

    public void update(boolean isLike) {
        this.isLike = isLike;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LikeHistory that = (LikeHistory) o;
        return that.getPost().getId().equals(this.post.getId()) &&
                that.getUser().getId().equals(this.user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUser(), getPost(), isLike());
    }
}
