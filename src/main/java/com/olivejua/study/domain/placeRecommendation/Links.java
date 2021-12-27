package com.olivejua.study.domain.placeRecommendation;

import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
@Embeddable
public class Links {
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Link> links = new ArrayList<>();

    public void replace(PlaceRecommendation post, List<String> links) {
        clear();

        links.forEach(e -> {
            Link newLink = Link.createLink(post, e);
            this.links.add(newLink);
        });
    }

    private void clear() {
        links.clear();
    }

    public int size() {
        return links.size();
    }

    public List<Link> getLinks() {
        return links;
    }

    public List<String> getElements() {
        return links.stream()
                .map(Link::getElement)
                .collect(Collectors.toList());
    }
}
