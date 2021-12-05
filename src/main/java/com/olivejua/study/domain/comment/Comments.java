package com.olivejua.study.domain.comment;

import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Embeddable
public class Comments {

    @OneToMany(mappedBy = "post")
    private final List<Comment> comments = new ArrayList<>();

    public void add(Comment comment) {
        comments.add(comment);
    }

    public void remove(Comment comment) {
        comments.remove(comment);
    }

    public int size() {
        return comments.size();
    }
}
