package com.olivejua.study.domain;

import lombok.*;

import javax.persistence.*;

import static lombok.AccessLevel.*;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Getter
public class User {

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
    public User(Long id, String name, String email, Role role, String socialCode) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.socialCode = socialCode;
    }

    /**
     * 프로필 변경
     */
    public void changeProfile(String name) {
        this.name = name;
    }

    /**
     * 회원가입
     */
    public User join(Role role) {
        this.role = role;

        return this;
    }
}
