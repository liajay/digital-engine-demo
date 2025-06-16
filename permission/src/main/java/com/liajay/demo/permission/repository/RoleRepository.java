package com.liajay.demo.permission.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.liajay.demo.permission.model.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByRole(String role);
}