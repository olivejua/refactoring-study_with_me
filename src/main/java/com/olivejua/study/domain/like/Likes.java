package com.olivejua.study.domain.like;

import com.olivejua.study.domain.post.Post;
import com.olivejua.study.domain.user.User;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

import static com.olivejua.study.domain.like.LikeStatus.DISLIKE;
import static com.olivejua.study.domain.like.LikeStatus.LIKE;

@NoArgsConstructor
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

    public int getSizeOfLikes() {
        return (int) likes.stream()
                .filter(Like::hasStatusOfLike)
                .count();
    }

    public int getSizeOfDislikes() {
        return (int) likes.stream()
                .filter(Like::hasStatusOfDislike)
                .count();
    }
}
