package com.olivejua.study.common.mockData;

import com.olivejua.study.domain.question.Question;
import com.olivejua.study.domain.user.User;

public class MockQuestion {

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private User author;
        private String title;
        private String content;

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

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Question build() {
            return Question.createPost(
                    id,
                    author,
                    title,
                    content
            );
        }
    }
}
