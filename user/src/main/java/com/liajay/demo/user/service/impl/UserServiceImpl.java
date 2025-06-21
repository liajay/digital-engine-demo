package com.liajay.demo.user.service.impl;

import com.liajay.demo.common.exception.ErrorCode;
import com.liajay.demo.user.exception.LoginException;
import com.liajay.demo.user.exception.RegisterException;
import com.liajay.demo.user.jwt.JwtToken;
import com.liajay.demo.user.jwt.JwtUtil;
import com.liajay.demo.user.jwt.security.JwtAuthenticationToken;
import com.liajay.demo.user.model.UserInfo;
import com.liajay.demo.user.model.UserWithPassword;
import com.liajay.demo.user.model.entity.User;
import com.liajay.demo.user.model.response.LoginResponse;
import com.liajay.demo.user.repository.UserRepository;
import com.liajay.demo.user.service.UserService;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
//    @Autowired
    private AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;

    private final PasswordEncoder passwordEncoder;

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
        Optional<User> userOpt = userRepository.findById(user.getId());
        if (userOpt.isPresent()){
            throw new RegisterException(ErrorCode.USERNAME_ALREADY_EXISTS);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
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

        User user = userRepository.findById(jwtToken.userId()).get();

        return new UserInfo(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPhone()
        );
    }

    @Override
    public void updateUserInfo(UserInfo userInfo) {
        JwtToken jwtToken = (JwtToken) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = userRepository.findById(jwtToken.userId()).get();

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
     }

    @Override
    public void resetPassword(String password) {
        JwtToken jwtToken = (JwtToken) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = userRepository.findById(jwtToken.userId()).get();

        user.setPassword(passwordEncoder.encode(password));

        userRepository.save(user);
    }


}
