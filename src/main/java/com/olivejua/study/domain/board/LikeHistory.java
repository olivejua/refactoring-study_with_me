package com.olivejua.study.domain.board;

import com.olivejua.study.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Getter
@IdClass(LikeHistoryPK.class)
@Entity
public class LikeHistory {

    @Id
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "POST_ID")
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
