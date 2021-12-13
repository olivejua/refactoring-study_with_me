package com.olivejua.study.domain.auth;

import com.olivejua.study.auth.JwtTokenProvider;
import com.olivejua.study.domain.BaseTimeEntity;
import com.olivejua.study.domain.user.User;
import lombok.Getter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Entity
public class AuthToken extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "USER_ID", nullable = false, unique = true)
    private User user;

    @Column(nullable = false)
    private String refreshToken;

    public static AuthToken createAuthToken(User user, String refreshToken) {
        AuthToken token = new AuthToken();
        token.user = user;
        token.refreshToken = refreshToken;

        return token;
    }

    public boolean isExpired(JwtTokenProvider provider) {
        return provider.validateToken(refreshToken);
    }

    public boolean equalsToken(String refreshToken) {
        return this.refreshToken.equals(refreshToken);
    }


    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
