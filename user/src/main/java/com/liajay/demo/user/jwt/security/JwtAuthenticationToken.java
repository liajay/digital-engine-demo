package com.liajay.demo.user.jwt.security;

import com.liajay.demo.user.jwt.JwtToken;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {
    private final JwtToken principal;
    private final String credentials;

    public JwtAuthenticationToken(String credentials) {
        super(null);
        this.principal = null;
        this.credentials = credentials;
    }

    public JwtAuthenticationToken(JwtToken principal, Collection<? extends GrantedAuthority> authorities){
        super(authorities);
        this.credentials = null;
        this.principal = principal;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }
}
