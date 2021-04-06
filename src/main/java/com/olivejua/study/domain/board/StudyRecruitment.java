package com.olivejua.study.domain.board;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static lombok.AccessLevel.PROTECTED;

@Entity
@DiscriminatorValue("recruit")
@NoArgsConstructor(access = PROTECTED)
@Getter
public class StudyRecruitment extends Board{

    @Embedded
    @Column(name = "CONDITION")
    private Condition condition;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private Comment comment;
}
