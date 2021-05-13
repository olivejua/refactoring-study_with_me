package com.olivejua.study.domain.board;

import com.olivejua.study.domain.User;
import lombok.Getter;

import javax.persistence.*;

@Getter
@IdClass(LikePK.class)
@Entity
public class Like {

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

    public void update(boolean isLike) {
        this.isLike = isLike;
    }
}
