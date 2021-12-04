package com.olivejua.study.domain;

import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
@Embeddable
public class Techs {

    @OneToMany(mappedBy = "post", cascade = ALL, orphanRemoval = true)
    private final List<Tech> techs = new ArrayList<>();

    public void replace(StudyRecruitment post, List<String> techs) {
        clear();

        techs.forEach(e -> {
            Tech tech = Tech.createTech(post, e);
            this.techs.add(tech);
        });
    }

    private void clear() {
        techs.clear();
    }
}
