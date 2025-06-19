package com.liajay.demo.user.jwt.security;

import com.liajay.demo.user.jwt.JwtToken;
import com.liajay.demo.user.jwt.JwtUtil;
import com.liajay.demo.user.model.entity.GrantedRole;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {
    private final JwtUtil jwtUtil;

    public JwtAuthenticationProvider(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
        String jwtTokenSigned = (String) jwtAuthenticationToken.getCredentials();

        JwtToken jwtToken = jwtUtil.parseAndVerify(jwtTokenSigned, Instant.now());
        List<GrantedRole> roles = new ArrayList<>();
        roles.add(new GrantedRole(jwtToken.role_id()));

        return new JwtAuthenticationToken(jwtToken,roles);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
