package com.olivejua.study.domain.studyRecruitment;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

import static javax.persistence.GenerationType.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Tech {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "TECH_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID")
    private StudyRecruitment post;

    private String element;

    public static Tech createTech(StudyRecruitment post, String element) {
        Tech ts = new Tech();
        ts.post = post;
        ts.element = element;

        return ts;
    }

    public boolean hasElement(String element) {
        return this.element.equals(element);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Tech) {
            Tech ts = (Tech) obj;
            return id.equals(ts.id);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(id);
    }
}
