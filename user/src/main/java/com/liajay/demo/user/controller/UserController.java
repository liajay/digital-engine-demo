package com.liajay.demo.user.controller;

import com.liajay.demo.common.feign.PermissionClient;
import com.liajay.demo.common.model.ApiResponse;
import com.liajay.demo.common.model.dto.RoleCode;
import com.liajay.demo.user.model.UserWithPassword;
import com.liajay.demo.user.model.response.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private final PermissionClient permissionClient;

    @Autowired
    public UserController(PermissionClient permissionClient) {
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
        LoginResponse loginResponse = new LoginResponse();
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
