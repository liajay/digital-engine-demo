package com.liajay.demo.permission.service;


import com.liajay.demo.common.model.dto.RoleCode;
import org.springframework.stereotype.Service;

@Service
public interface PermissionService {
    void bindDefaultRole(Long userId);

    RoleCode getUserRoleCode(Long userId);

    void upgradeToAdmin(Long userId);

    void downgradeToUser(Long userId);
}


