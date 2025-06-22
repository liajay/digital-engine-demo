package com.liajay.demo.permission.service.impl;

import com.liajay.demo.common.model.dto.RoleCode;
import com.liajay.demo.permission.model.entity.Role;
import com.liajay.demo.permission.model.entity.UserRole;
import com.liajay.demo.permission.repository.UserRoleRepository;
import com.liajay.demo.permission.service.PermissionService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PermissionServiceImpl implements PermissionService {
    private final UserRoleRepository userRoleRepository;

    public PermissionServiceImpl(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public void bindDefaultRole(Long userId) {
        UserRole userRole = new UserRole(Role.USER(), userId);

        userRoleRepository.save(userRole);
    }

    @Override
    public RoleCode getUserRoleCode(Long userId) {
        Optional<UserRole> userRoleOptional = userRoleRepository.findByUserId(userId);
        if (!userRoleOptional.isPresent()){
            return RoleCode.USER;
        }
        UserRole userRole = userRoleOptional.get();

        return new RoleCode(userRole.getRole().getId());
    }

    @Override
    public void upgradeToAdmin(Long userId) {
        Optional<UserRole> userRoleOptional = userRoleRepository.findByUserId(userId);
        if (!userRoleOptional.isPresent()){
            return;
        }

        UserRole userRole = userRoleOptional.get();
        userRole.setRole(Role.ADMIN());
        userRoleRepository.save(userRole);
    }

    @Override
    public void downgradeToUser(Long userId) {
        Optional<UserRole> userRoleOptional = userRoleRepository.findByUserId(userId);
        if (!userRoleOptional.isPresent()){
            return;
        }

        UserRole userRole = userRoleOptional.get();
        userRole.setRole(Role.SUPER_ADMIN());
        userRoleRepository.save(userRole);
    }
}
