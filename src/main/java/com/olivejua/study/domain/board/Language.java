package com.olivejua.study.domain.board;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Language {

    @Id @GeneratedValue
    @Column(name = "LANGUAGE_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID")
    private StudyRecruitment post;

    @Column(name = "LANGUAGE")
    private String language;

    public Language(StudyRecruitment post, String language) {
        this.post = post;
        this.language = language;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Language) {
            Language lan = (Language) obj;
            return (this.language.equals(lan.getLanguage())
                    && this.post.getId().equals(lan.getPost().getId()));
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPost(), getLanguage());
    }

    @Override
    public String toString() {
        return "Language{" +
                "id=" + id +
                ", postId=" + post.getId() +
                ", language='" + language + '\'' +
                '}';
    }
}
