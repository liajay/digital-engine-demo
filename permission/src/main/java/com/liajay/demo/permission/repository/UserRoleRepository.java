package com.liajay.demo.permission.repository;

import com.liajay.demo.permission.model.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    Optional<UserRole> findByUserId(Long userId);
    boolean existsByUserId(Long userId);
}