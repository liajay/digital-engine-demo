package com.liajay.demo.permission.controller;

import com.liajay.demo.common.model.ApiResponse;
import com.liajay.demo.common.model.dto.RoleCode;
import com.liajay.demo.permission.repository.UserRoleRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/role")
public class PermissionController {

    @GetMapping("/{id}")
    public ApiResponse<RoleCode> getUserRoleCode(@PathVariable(name = "id") long id){
        return ApiResponse.success(RoleCode.USER);
    }

    @PostMapping("/{id}")
    public ApiResponse<String> bindDefaultRole(@PathVariable(name = "id") long id){

        return ApiResponse.success("success");
    }
}
