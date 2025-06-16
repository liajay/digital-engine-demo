package com.liajay.demo.permission.controller;

import com.liajay.demo.common.model.ApiResponse;
import com.liajay.demo.common.model.dto.RoleCode;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/role")
public class RoleController {
    @GetMapping("/{id}")
    public ApiResponse<RoleCode> getUserRoleCode(@PathVariable(name = "id") long id){
        return ApiResponse.success(RoleCode.USER);
    }
}
