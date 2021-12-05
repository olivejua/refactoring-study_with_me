package com.olivejua.study.domain;

import lombok.*;

import javax.persistence.*;

import java.util.Objects;

import static javax.persistence.EnumType.*;
import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Entity
@NoArgsConstructor(access = PROTECTED)
public class User extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "USER_ID")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Enumerated(STRING)
    @Column(nullable = false)
    private Role role;

    @Enumerated(STRING)
    @Column(nullable = false)
    private SocialCode socialCode;

    private User(String name, String email, Role role, SocialCode socialCode) {
        this.name = name;
        this.email = email;
        this.role = role;
        this.socialCode = socialCode;
    }

    public static User createUser(String name, String email, Role role, SocialCode socialCode) {
        return new User(name, email, role, socialCode);
    }

    public static User createUser(Long id, String name, String email, Role role, SocialCode socialCode) {
        User user = new User(name, email, role, socialCode);
        user.id = id;

        return user;
    }

    /**
     * 회원가입
     */
    public void changeRoleToUser() {
        this.role = Role.USER;
    }


    /**
     * 프로필 변경
     */
    public void changeProfile(String name) {
        this.name = name;
    }

    /**
     * 소셜정보와 맞춰서 이름 업데이트
     */
    public User updateName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Getter
     */

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }

    public String getNameOfRole() {
        return role.name();
    }

    public SocialCode getSocialCode() {
        return socialCode;
    }

    /**
     * equals and hashcode
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, role, socialCode);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", socialCode=" + socialCode +
                '}';
    }
}
