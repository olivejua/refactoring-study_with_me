package com.olivejua.study.auth.dto;

import com.olivejua.study.domain.user.Role;
import com.olivejua.study.domain.user.SocialCode;
import com.olivejua.study.domain.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class AuthenticatedUser implements UserDetails, OAuth2User {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String email;
    private Role role;
    private SocialCode socialCode;

    private Map<String, Object> attributes;

    public AuthenticatedUser(User user) {
        id = user.getId();
        name = user.getName();
        email = user.getEmail();
        role = user.getRole();
        socialCode = user.getSocialCode();
    }

    public AuthenticatedUser(User user, Map<String, Object> attributes) {
        this(user);
        this.attributes = attributes;
    }

    public Long getId() {
        return id;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collet = new ArrayList<>();
        collet.add(()-> role.getKey());
        return collet;
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return name;
    }

    public User toEntity() {
        return User.createUser(id, name, email, role, socialCode);
    }
}
