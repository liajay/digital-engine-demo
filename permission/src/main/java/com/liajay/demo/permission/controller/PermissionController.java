package com.liajay.demo.permission.controller;

import com.liajay.demo.common.model.ApiResponse;
import com.liajay.demo.common.model.dto.RoleCode;
import com.liajay.demo.permission.repository.UserRoleRepository;
import com.liajay.demo.permission.service.PermissionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/role")
public class PermissionController {
    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @GetMapping("/{id}")
    public ApiResponse<RoleCode> getUserRoleCode(@PathVariable(name = "id") long id){
        return ApiResponse.success(permissionService.getUserRoleCode(id));
    }

    @PostMapping("/{id}")
    public ApiResponse<String> bindDefaultRole(@PathVariable(name = "id") long id){
        permissionService.bindDefaultRole(id);
        return ApiResponse.success("success");
    }

    @PostMapping("/reset/admin/{id}")
    public ApiResponse<String> upgradeToAdmin(@PathVariable(name = "id") long id){
        permissionService.upgradeToAdmin(id);
        return ApiResponse.success("success");
    }

    @PostMapping("reset/user/{id}")
    public ApiResponse<String> downToUser(@PathVariable(name = "id") long id){
        permissionService.downgradeToUser(id);
        return ApiResponse.success("success");
    }
}
