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

    @Builder
    public User(String name, String email, Role role, String socialCode) {
        this.name = name;
        this.email = email;
        this.role = role;
        this.socialCode = socialCode;
    }

    /**
     * 회원가입
     */
    public void join() {
        this.role = Role.USER;
    }

    /**
     * 프로필 변경
     */
    public void changeProfile(String name) {
        this.name = name;
    }
}
