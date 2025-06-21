package com.liajay.demo.user.service.impl;


import com.liajay.demo.common.exception.ErrorCode;
import com.liajay.demo.common.exception.SystemException;
import com.liajay.demo.common.feign.PermissionClient;
import com.liajay.demo.common.model.ApiResponse;
import com.liajay.demo.common.model.dto.RoleCode;
import com.liajay.demo.user.model.UserWithPassword;
import com.liajay.demo.user.model.entity.GrantedRole;
import com.liajay.demo.user.model.entity.User;
import com.liajay.demo.user.repository.UserRepository;
//import org.springframework.security.core.userdetails.User;
import feign.FeignException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    private PermissionClient permissionClient;

    public UserDetailsServiceImpl(PermissionClient permissionClient, UserRepository userRepository) {
        this.permissionClient = permissionClient;
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOpt = userRepository.findByUsername(username);

        User user = userOpt.orElseThrow(
                () -> new UsernameNotFoundException("")
        );

        GrantedRole role = null;
        try{
            ApiResponse<RoleCode> response = permissionClient.getUserRoleCode(user.getId());
            if (response.isSuccess()){
                role = new GrantedRole (((ApiResponse.Success<RoleCode>) response).getData());
            } else {
                role = new GrantedRole(RoleCode.USER);
            }
        }catch (FeignException.FeignClientException e){
            throw new SystemException(ErrorCode.SERVICE_UNAVAILABLE, e);
        }

        return new UserWithPassword()
                .setId(user.getId())
                .setUsername(user.getUsername())
                .setPassword(user.getPassword())
                .setRole(role);
    }
}
