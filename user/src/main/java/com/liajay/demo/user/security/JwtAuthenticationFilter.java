package com.liajay.demo.user.security;

import com.liajay.demo.user.jwt.JwtToken;
import com.liajay.demo.user.jwt.JwtUtil;
import com.liajay.demo.user.jwt.exception.JwtVerifyException;
import com.liajay.demo.user.jwt.security.JwtAuthenticationToken;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;
import java.util.Optional;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        Optional<String> tokenOpt = Optional.ofNullable(getTokenFromRequest(request));
        do {
            if(tokenOpt.isEmpty()){
                break;
            }

            String token = tokenOpt.get();

            JwtAuthenticationToken unAuthenticateToken = new JwtAuthenticationToken(token);

            try{
                JwtAuthenticationToken authenticatedToken = (JwtAuthenticationToken) authenticationManager.authenticate(
                        unAuthenticateToken
                );
                SecurityContextHolder.getContext().setAuthentication(authenticatedToken);
            } catch (AuthenticationException e) {
                throw new JwtVerifyException(null);
            }

        }while (false);

        filterChain.doFilter(request, response);
    }


    private String getTokenFromRequest(HttpServletRequest request) {
        var bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }
}
