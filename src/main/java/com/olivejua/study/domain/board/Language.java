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

    private String language;

    // post 없이 Languages만 조회할 일 없음
    public Language(String language) {
        this.language = language;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Language) {
            Language lan = (Language) obj;
            return language.equals(lan.language);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(getId(), getPost(), getLanguage());
    }
}
