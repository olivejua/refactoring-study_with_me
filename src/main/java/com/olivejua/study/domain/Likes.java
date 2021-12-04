package com.olivejua.study.domain;

import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

import static com.olivejua.study.domain.LikeStatus.DISLIKE;
import static com.olivejua.study.domain.LikeStatus.LIKE;
import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
@Embeddable
public class Likes {

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Like> likes = new ArrayList<>();

    public void addLike(Post post, User user) {
        add(post, user, LIKE);
    }

    public void addDislike(Post post, User user) {
        add(post, user, DISLIKE);
    }

    private void add(Post post, User user, LikeStatus status) {
        likes.add(Like.createLike(post, user, status));
    }
}
