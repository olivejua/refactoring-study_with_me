package com.olivejua.study.common.mockData;

import com.olivejua.study.domain.studyRecruitment.Condition;
import com.olivejua.study.domain.studyRecruitment.StudyRecruitment;
import com.olivejua.study.domain.user.User;

import java.util.ArrayList;
import java.util.List;

public class MockStudyRecruitment {

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private User author;
        private String title;
        private List<String> techs = new ArrayList<>();
        private Condition condition;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder author(User author) {
            this.author = author;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder techs(List<String> techs) {
            this.techs.addAll(techs);
            return this;
        }

        public Builder condition(Condition condition) {
            this.condition = condition;
            return this;
        }

        public StudyRecruitment build() {
            return StudyRecruitment.createPost(
                    id,
                    author,
                    title,
                    techs,
                    condition
            );
        }
    }
}
