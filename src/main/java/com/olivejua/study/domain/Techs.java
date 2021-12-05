package com.olivejua.study.domain;

import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static javax.persistence.CascadeType.ALL;
import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
@Embeddable
public class Techs {

    @OneToMany(mappedBy = "post", cascade = ALL, orphanRemoval = true)
    private final List<Tech> techs = new ArrayList<>();

    public void replace(StudyRecruitment post, List<String> techs) {
        clear();
        this.techs.addAll(mapToTechList(post, techs));
    }

    private List<Tech> mapToTechList(StudyRecruitment post, List<String> techs) {
        return techs.stream()
            .map(tech -> Tech.createTech(post, tech))
            .collect(Collectors.toList());
    }

    private void clear() {
        techs.clear();
    }

    public boolean containsAll(List<String> techElements) {
        if (techElements.size() != techs.size()) {
            return false;
        }

        List<String> elements = techs.stream()
                .map(Tech::getElement)
                .collect(Collectors.toList());
        return techElements.containsAll(elements);
    }

    public int size() {
        return techs.size();
    }
}
