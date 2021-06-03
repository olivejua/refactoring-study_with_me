package com.olivejua.study.domain;

import lombok.*;

import javax.persistence.*;

import static lombok.AccessLevel.*;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Getter
public class User extends BaseTimeEntity {

    @Id @GeneratedValue
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
}
