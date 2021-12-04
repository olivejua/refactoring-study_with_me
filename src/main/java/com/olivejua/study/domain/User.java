package com.olivejua.study.domain;

import lombok.*;

import javax.persistence.*;

import java.util.Objects;

import static lombok.AccessLevel.*;

@Entity
@NoArgsConstructor(access = PROTECTED)
public class User extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private String socialCode;

    public static User createUser(String name, String email, Role role, String socialCode) {
        User newUser = new User();

        newUser.name = name;
        newUser.email = email;
        newUser.role = role;
        newUser.socialCode = socialCode;

        return newUser;
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
}
