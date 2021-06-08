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

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TECHSTACK_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID")
    private StudyRecruitment post;

    private String element;

    public void changeTechStack(StudyRecruitment post, String element) {
        this.post = post;
        this.element = element;
        post.getTechStack().add(this);
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
