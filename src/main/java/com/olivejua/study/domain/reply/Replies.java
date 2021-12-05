package com.olivejua.study.domain.reply;

import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Embeddable
public class Replies {

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Reply> replies = new ArrayList<>();

    public void add(Reply reply) {
        replies.add(reply);
    }

    public void remove(Reply reply) {
        replies.remove(reply);
    }
}
