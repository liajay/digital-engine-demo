package com.liajay.demo.permission.init;

import com.liajay.demo.common.model.dto.RoleCode;
import com.liajay.demo.permission.model.entity.Role;
import com.liajay.demo.permission.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import jakarta.transaction.Transactional;

import java.util.List;

@Component
public class RoleInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public RoleInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        if (roleRepository.count() == 0) {
            Role superAdmin = new Role(RoleCode.SUPER_ADMIN.getRoleCode(), "super_admin");
            Role user = new Role(RoleCode.USER.getRoleCode(), "user");
            Role admin = new Role(RoleCode.ADMIN.getRoleCode(), "admin");

            roleRepository.saveAll(List.of(superAdmin, user, admin));
            System.out.println("初始化角色数据完成");
        }
    }
}