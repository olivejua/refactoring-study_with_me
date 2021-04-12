package com.olivejua.study.domain.board;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Link {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID")
    private PlaceRecommendation post;

    private String link;

    public Link(String link) {
        this.link = link;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Link) {
            Link temp = (Link) obj;
            return link.equals(temp.link);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPost(), getLink());
    }
}
