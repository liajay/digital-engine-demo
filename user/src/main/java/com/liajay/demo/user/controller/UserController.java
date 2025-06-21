package com.liajay.demo.user.controller;

import com.liajay.demo.common.feign.PermissionClient;
import com.liajay.demo.common.model.ApiResponse;
import com.liajay.demo.common.model.dto.RoleCode;
import com.liajay.demo.user.jwt.JwtToken;
import com.liajay.demo.user.jwt.JwtUtil;
import com.liajay.demo.user.model.UserInfo;
import com.liajay.demo.user.model.UserWithPassword;
import com.liajay.demo.user.model.entity.User;
import com.liajay.demo.user.model.response.LoginResponse;
import com.liajay.demo.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/user")
public class UserController { ;
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ApiResponse<String> register(@RequestBody User user){
        userService.register(user);
        return ApiResponse.success("Registration successful");
    };

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@RequestBody User user){
        return ApiResponse.success(userService.login(user));
    }

    @GetMapping("/{id}")
    public ApiResponse<UserInfo> getUserInfo(@PathVariable(name = "id") long id){
        return ApiResponse.success(userService.findUserInfo(id));
    }

    @PutMapping("/{id}")
    public ApiResponse<String> updateUserInfo(@RequestBody UserInfo userInfo){
        userService.updateUserInfo(userInfo);
        return ApiResponse.success("successes");
    }

    @PostMapping("/reset-password")
    public ApiResponse<String> updateUserPassword(@RequestBody String password){
        userService.resetPassword(password);
        return ApiResponse.success("successes");
    }
}
