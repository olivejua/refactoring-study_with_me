package com.olivejua.study.auth;

import com.olivejua.study.auth.dto.GuestUser;
import com.olivejua.study.auth.dto.LoginUser;
import com.olivejua.study.domain.user.Role;
import com.olivejua.study.domain.user.SocialCode;
import com.olivejua.study.service.user.UserService;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

import static org.apache.http.HttpHeaders.AUTHORIZATION;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    // 토큰 유효시간 30분
    private long tokenValidTime = 30 * 60 * 1000L;
    private final String USER_ID = "userId";
    private final String USERNAME = "username";
    private final String USER_EMAIL = "email";
    private final String SOCIAL_CODE = "socialCode";
    private final String USER_ROLE = "role";

    private final UserService userService;

    @PostConstruct
    protected void Init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createTokenForUser(Long userId) {
        Date now = new Date();
        Date expirationTime = new Date(now.getTime() + tokenValidTime);

        return Jwts.builder()
                .claim(USER_ROLE, Role.USER)
                .claim(USER_ID, userId)
                .setIssuedAt(now)
                .setExpiration(expirationTime)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String createRefreshTokenForUser(Long userId) {
        long tokenValidTime = 14 * 24 * 60 * 60 * 1000L; //2주

        Date now = new Date();
        Date expirationTime = new Date(now.getTime() + tokenValidTime);

        return Jwts.builder()
                .claim(USER_ROLE, Role.USER)
                .claim(USER_ID, userId)
                .setIssuedAt(now)
                .setExpiration(expirationTime)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String createTokenForGuest(String email, String name, SocialCode socialCode) {
        Date now = new Date();
        Date expirationTime = new Date(now.getTime() + tokenValidTime);

        return Jwts.builder()
                .claim(USER_ROLE, Role.GUEST)
                .claim(USERNAME, name)
                .claim(USER_EMAIL, email)
                .claim(SOCIAL_CODE, socialCode)
                .setIssuedAt(now)
                .setExpiration(expirationTime)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        LoginUser user = this.parseLoginUserByToken(token);
        UserDetails userDetails = userService.loadUserByUsername(user.getId() + "");
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public LoginUser parseLoginUserByToken(String token) {
        try {
            if (token.startsWith("Bearer")) {
                token = token.replace("Bearer", "");
            }

            Long userId = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody()
                    .get(USER_ID, Long.class);

            return new LoginUser(userId);
        } catch (JwtException | IllegalArgumentException e) {
            throw new IllegalArgumentException();
        }
    }

    public GuestUser parseGuestUserByToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();

        String username = claims.get(USERNAME, String.class);
        String userEmail = claims.get(USER_EMAIL, String.class);
        String socialCode = claims.get(SOCIAL_CODE, String.class);
        return new GuestUser(username, userEmail, SocialCode.findByName(socialCode));
    }

    public String resolveToken(HttpServletRequest request) {
        return request.getHeader(AUTHORIZATION);
    }

    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
