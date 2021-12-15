package com.olivejua.study.unit.studyRecruitment;

import com.olivejua.study.domain.studyRecruitment.Condition;
import com.olivejua.study.domain.studyRecruitment.StudyRecruitment;
import com.olivejua.study.domain.user.Role;
import com.olivejua.study.domain.user.SocialCode;
import com.olivejua.study.domain.user.User;

import java.time.LocalDate;
import java.util.List;

public class StudyRecruitmentTest {
    User author = User.createUser(1L, "authorA", "authorA@gmail.com", Role.USER, SocialCode.GOOGLE);
    StudyRecruitment post = StudyRecruitment.createPost(1L, author, "test title", List.of("java", "spring boot"), makeCondition());

    private Condition makeCondition() {
        return Condition.builder()
                .meetingPlace("강남")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusMonths(3))
                .capacity(10)
                .explanation("")
                .build();
    }
}
