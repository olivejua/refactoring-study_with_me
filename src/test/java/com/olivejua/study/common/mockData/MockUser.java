package com.olivejua.study.common.mockData;

import com.olivejua.study.domain.user.Role;
import com.olivejua.study.domain.user.SocialCode;
import com.olivejua.study.domain.user.User;

public class MockUser {
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String name;
        private String email;
        private Role role;
        private SocialCode socialCode;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder role(Role role) {
            this.role = role;
            return this;
        }

        public Builder socialCode(SocialCode socialCode) {
            this.socialCode = socialCode;
            return this;
        }

        public User build() {
            return User.createUser(
                    id,
                    name,
                    email,
                    role,
                    socialCode
            );
        }
    }
}
