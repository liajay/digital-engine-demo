package com.liajay.demo.user.controller;

import com.liajay.demo.common.feign.PermissionClient;
import com.liajay.demo.common.model.ApiResponse;
import com.liajay.demo.common.model.dto.RoleCode;
import com.liajay.demo.user.jwt.JwtToken;
import com.liajay.demo.user.jwt.JwtUtil;
import com.liajay.demo.user.model.UserWithPassword;
import com.liajay.demo.user.model.response.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/user")
public class UserController {
    private final PermissionClient permissionClient;
    private final JwtUtil jwtUtil;

    @Autowired
    public UserController(JwtUtil jwtUtil, PermissionClient permissionClient) {
        this.jwtUtil = jwtUtil;
        this.permissionClient = permissionClient;
    }

    @PostMapping("/register")
    public ApiResponse<String> register(@RequestBody UserWithPassword userWithPassword){
        return ApiResponse.success("Registration successful");
    };

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@RequestBody UserWithPassword userWithPassword){

        ApiResponse<RoleCode> apiResponse = permissionClient.getUserRoleCode(userWithPassword.getId());
        RoleCode roleCode;
        if (apiResponse.isSuccess()){
            roleCode = ((ApiResponse.Success<RoleCode>) apiResponse).getData();
        }
        String signedJwt = jwtUtil.encode(1,RoleCode.USER);

        LoginResponse loginResponse = new LoginResponse(1000L ,signedJwt);
        return ApiResponse.success(loginResponse);
    }

    @GetMapping("/{id}")
    public ApiResponse<UserWithPassword> getUserInfo(@PathVariable(name = "id") long id){
        UserWithPassword userWithPassword = new UserWithPassword();
        userWithPassword.setId(id);
        userWithPassword.setUsername("test");
        userWithPassword.setPassword("123");
        return ApiResponse.success(userWithPassword);
    }

    @PutMapping("/{id}")
    public ApiResponse<String> updateUserInfo(@RequestBody UserWithPassword userWithPassword){
        return ApiResponse.success("successes");
    }

    @PostMapping("/reset-password")
    public ApiResponse<String> updateUserPassword(@RequestBody String password){
        return ApiResponse.success("successes");
    }
}
