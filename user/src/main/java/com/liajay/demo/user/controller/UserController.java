package com.liajay.demo.user.controller;

import com.liajay.demo.common.model.ApiResponse;
import com.liajay.demo.common.model.dto.RoleCode;
import com.liajay.demo.common.model.dto.UserInfo;
import com.liajay.demo.common.model.dto.UserOperationLog;
import com.liajay.demo.user.exception.LoginException;
import com.liajay.demo.user.model.entity.User;
import com.liajay.demo.user.model.response.LoginResponse;
import com.liajay.demo.user.model.response.UpdateUserInfoResponse;
import com.liajay.demo.user.service.LogProducerService;
import com.liajay.demo.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

//TODO: 目前无论是否抛出异常都会记录日志, 后续再处理一下
@RestController
@RequestMapping("/user")
public class UserController { ;
    private final UserService userService;

    private final LogProducerService logProducerService;
    public UserController(UserService userService, LogProducerService logProducerService) {
        this.logProducerService = logProducerService;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ApiResponse<String> register(@RequestBody User user, HttpServletRequest request){
        logProducerService.sendOperationLog(new UserOperationLog.Register(
                user.getId(),
                request.getRemoteAddr(),
                user.toUserInfo()
        ));

        userService.register(user);
        return ApiResponse.success("Registration successful");
    };


    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@RequestBody User user, HttpServletRequest request){

        logProducerService.sendOperationLog(new UserOperationLog.Login(
                user.getId(),
                request.getRemoteAddr()
        ));

        return ApiResponse.success(userService.login(user));

    }

    @GetMapping("/{id}")
    public ApiResponse<UserInfo> getUserInfo(@PathVariable(name = "id") long id){
        return ApiResponse.success(userService.findUserInfo(id));
    }

    @PutMapping("/{id}")
    public ApiResponse<UpdateUserInfoResponse> updateUserInfo(@RequestBody UserInfo userInfo, HttpServletRequest request){
        UpdateUserInfoResponse response = userService.updateUserInfo(userInfo);

        logProducerService.sendOperationLog(new UserOperationLog.UpdateUser(
                userInfo.id(),
                request.getRemoteAddr(),
                userInfo,
                response.getOldUserInfo()
        ));

        return ApiResponse.success(response);
    }

    @PostMapping("/reset-password/{id}")
    public ApiResponse<String> updateUserPassword(@PathVariable(name = "id") long resetUserId ,@RequestBody String password){
        userService.resetPassword(password, resetUserId);
        return ApiResponse.success("successes");
    }


}
