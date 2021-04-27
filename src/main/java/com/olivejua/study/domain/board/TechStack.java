package com.olivejua.study.domain.board;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

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

    // post 없이 Languages만 조회할 일 없음
    public TechStack(StudyRecruitment post, String element) {
        this.post = post;
        this.element = element;
    }

    public void setPost(StudyRecruitment post) {
        this.post = post;
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
