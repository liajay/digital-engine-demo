package com.liajay.demo.user.service.impl;

import com.liajay.demo.common.exception.BizException;
import com.liajay.demo.common.exception.ErrorCode;
import com.liajay.demo.common.exception.SystemException;
import com.liajay.demo.common.feign.PermissionClient;
import com.liajay.demo.common.model.ApiResponse;
import com.liajay.demo.common.model.dto.RoleCode;
import com.liajay.demo.user.exception.LoginException;
import com.liajay.demo.user.exception.PermissionDeniedException;
import com.liajay.demo.user.exception.RegisterException;
import com.liajay.demo.user.jwt.JwtToken;
import com.liajay.demo.user.jwt.JwtUtil;
import com.liajay.demo.common.model.dto.UserInfo;
import com.liajay.demo.user.model.UserWithPassword;
import com.liajay.demo.user.model.entity.User;
import com.liajay.demo.user.model.response.LoginResponse;
import com.liajay.demo.user.model.response.UpdateUserInfoResponse;
import com.liajay.demo.user.repository.UserRepository;
import com.liajay.demo.user.service.UserService;
import feign.FeignException;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
//    @Autowired
    private AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    private PermissionClient permissionClient;
    public UserServiceImpl(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            JwtUtil jwtUtil,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void register(User user) {
        Optional<User> userOpt = userRepository.findByUsername(user.getUsername());
        if (userOpt.isPresent()){
            throw new RegisterException(ErrorCode.USERNAME_ALREADY_EXISTS);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));



        userRepository.save(user);
        user = userRepository.findByUsername(user.getUsername()).get();

        permissionClient.bindDefaultRole(user.getId());

    }

    @Override
    @Transactional
    @Nonnull
    public LoginResponse login(User user) {
//        userRepository.findById(user.getId()).orElseThrow(
//                () -> new RegisterException(ErrorCode.USER_NOT_FOUND)
//        );

        UsernamePasswordAuthenticationToken authenticationToken = null;
        try {
            authenticationToken = (UsernamePasswordAuthenticationToken) authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );

            UserWithPassword userPwd = (UserWithPassword) authenticationToken.getPrincipal();
            return new LoginResponse(
                    userPwd.getId(),
                    jwtUtil.getExpire().getSeconds(),
                    jwtUtil.encode(userPwd.getId())
            );
        } catch (BadCredentialsException e) {
            throw new LoginException(ErrorCode.USERNAME_OR_PASSWORD_BAD);
        } catch (UsernameNotFoundException e) {
            throw new LoginException(ErrorCode.USER_NOT_FOUND);
        }
    }

    @Override
    @Transactional
    public UserInfo findUserInfo(long userId) {
        var context  = SecurityContextHolder.getContext();
        JwtToken jwtToken = (JwtToken) context.getAuthentication().getPrincipal();

        allowToUpdate(jwtToken.userId(), userId);

        User user = userRepository.findById(userId).get();

        return new UserInfo(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPhone()
        );
    }

    @Override
    public UpdateUserInfoResponse updateUserInfo(UserInfo userInfo) {
        JwtToken jwtToken = (JwtToken) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        allowToUpdate(jwtToken.userId(), userInfo.id());

        User user = userRepository.findById(userInfo.id()).get();

        UpdateUserInfoResponse ret = new UpdateUserInfoResponse(user.toUserInfo());

        if (userInfo.email() != null){
            user.setEmail(userInfo.email());
        }

        if (userInfo.phone() != null){
            user.setPhone(userInfo.phone());
        }

        if (userInfo.username() != null){
            user.setUsername(userInfo.username());
        }

        userRepository.save(user);

        return ret;
     }

    @Override
    public void resetPassword(String password, long targetId) {
        JwtToken jwtToken = (JwtToken) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        allowToUpdate(jwtToken.userId(), targetId);

        User user = userRepository.findById(targetId).get();

        user.setPassword(passwordEncoder.encode(password));

        userRepository.save(user);
    }



    private RoleCode getRoleCode(long userId){
        try {
            ApiResponse<RoleCode> response = permissionClient.getUserRoleCode(userId);
            if (response.isSuccess()){
                return ((ApiResponse.Success<RoleCode>) response).getData();
            }

            throw new BizException(ErrorCode.USER_NOT_FOUND, "用户未找到" + userId);
        }catch (FeignException e){
            throw new SystemException(ErrorCode.SERVICE_UNAVAILABLE, e);
        }
    }

    private boolean allowToUpdate(long selfUserId, long targetUserId){
        if(selfUserId == targetUserId){
            return true;
        }

        if (isNotExist(selfUserId) || isNotExist(targetUserId)){
            throw new BizException(ErrorCode.USER_NOT_FOUND,"用户不存在");
        }

        RoleCode self = getRoleCode(selfUserId);

        if (self.equals(RoleCode.SUPER_ADMIN)) return true;

        RoleCode target = getRoleCode(targetUserId);

        if (self.equals(RoleCode.ADMIN) && target.equals(RoleCode.USER)) return true;

        throw new PermissionDeniedException();

    }

    private boolean isNotExist(long userId){
        Optional<User> optionalUser = userRepository.findById(userId);

        return optionalUser.isEmpty();
    }
}
