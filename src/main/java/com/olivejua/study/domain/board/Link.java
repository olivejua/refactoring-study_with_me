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

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LINK_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID")
    private PlaceRecommendation post;

    private String element;

    public Link(PlaceRecommendation post, String element) {
        this.post = post;
        this.element = element;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Link) {
            Link temp = (Link) obj;
            return element.equals(temp.element);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPost(), getElement());
    }
}
