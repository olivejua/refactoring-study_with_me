package com.olivejua.study.domain;

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

    public static Link createLink(PlaceRecommendation post, String element) {
        Link link = new Link();
        link.post = post;
        link.element = element;

        return link;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Link) {
            Link temp = (Link) obj;
            return id.equals(temp.id);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPost(), getElement());
    }
}
