package com.liajay.demo.common.feign;

import com.liajay.demo.common.model.ApiResponse;
import com.liajay.demo.common.model.dto.RoleCode;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "permission-service", path = "/role")
public interface PermissionClient {
    @GetMapping("{id}")
    public ApiResponse<RoleCode> getUserRoleCode(@PathVariable(name = "id") long id);
}
