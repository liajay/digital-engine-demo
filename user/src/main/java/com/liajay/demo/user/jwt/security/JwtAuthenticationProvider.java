package com.liajay.demo.user.jwt.security;

import com.liajay.demo.common.exception.ErrorCode;
import com.liajay.demo.common.exception.SystemException;
import com.liajay.demo.common.feign.PermissionClient;
import com.liajay.demo.common.model.ApiResponse;
import com.liajay.demo.common.model.dto.RoleCode;
import com.liajay.demo.user.jwt.JwtToken;
import com.liajay.demo.user.jwt.JwtUtil;
import com.liajay.demo.user.jwt.exception.JwtVerifyException;
import com.liajay.demo.user.model.entity.GrantedRole;
import feign.FeignException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {
    private final JwtUtil jwtUtil;

    private final PermissionClient permissionClient;

    public JwtAuthenticationProvider(JwtUtil jwtUtil, PermissionClient permissionClient) {
        this.jwtUtil = jwtUtil;
        this.permissionClient = permissionClient;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
        String jwtTokenSigned = (String) jwtAuthenticationToken.getCredentials();

        JwtToken jwtToken = jwtUtil.parseAndVerify(jwtTokenSigned, Instant.now());
        RoleCode roleCode = null;
        try {
            ApiResponse<RoleCode> response = permissionClient.getUserRoleCode(jwtToken.userId());
            if (response.isSuccess()){
                roleCode = ((ApiResponse.Success<RoleCode>) response).getData();
            }
        } catch (FeignException e) {
            throw new SystemException(ErrorCode.SERVICE_UNAVAILABLE, e);
        }

        if (roleCode == null){
            throw new JwtVerifyException(jwtToken);
        }

        List<GrantedRole> roles = new ArrayList<>();
        roles.add(new GrantedRole(roleCode.getRoleCode()));

        return new JwtAuthenticationToken(jwtToken,roles);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
