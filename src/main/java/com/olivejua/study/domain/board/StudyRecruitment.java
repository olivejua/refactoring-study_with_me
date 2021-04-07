package com.olivejua.study.domain.board;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Entity
@DiscriminatorValue("recruit")
@NoArgsConstructor(access = PROTECTED)
@Getter
public class StudyRecruitment extends Board {

    @Embedded
    @Column(name = "CONDITION")
    private Condition condition;

    @OneToMany(mappedBy = "board")
    private List<Comment> comment = new ArrayList<>();

    @Builder
    public StudyRecruitment(Condition condition) {
        this.condition = condition;
    }

    /**
     * 글 수정
     */
    public void edit(String title, Condition condition) {
        editTitle(title);
        this.condition = condition;
    }
}
