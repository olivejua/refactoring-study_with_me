package com.olivejua.study.domain.board;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TechStack {

    @Id @GeneratedValue
    @Column(name = "TECHSTACK_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID")
    private StudyRecruitment post;

    private String element;

    // post 없이 TechStack만 조회할 일 없음
    private TechStack(StudyRecruitment post, String element) {
        this.post = post;
        this.element = element;
    }

    public static List<TechStack> createTechStacks(StudyRecruitment post, List<String> elements) {
        return elements.stream()
                .map(e -> new TechStack(post, e))
                .collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TechStack) {
            TechStack ts = (TechStack) obj;
            return element.equals(ts.element);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(getId(), getPost(), getElement());
    }
}
