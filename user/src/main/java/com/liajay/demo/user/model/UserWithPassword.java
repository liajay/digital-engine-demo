package com.liajay.demo.user.model;


import com.liajay.demo.user.model.entity.GrantedRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserWithPassword implements UserDetails {
    private long id;

    private String username;

    private String password;

    private GrantedRole role;

    public long getId() {
        return id;
    }

    public UserWithPassword setId(long id) {
        this.id = id;
        return this;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public GrantedRole getRole() {
        return role;
    }

    public UserWithPassword setPassword(String password) {
        this.password = password;
        return this;
    }

    public UserWithPassword setRole(GrantedRole role) {
        this.role = role;
        return this;
    }

    public UserWithPassword setUsername(String username) {
        this.username = username;
        return this;
    }
}
