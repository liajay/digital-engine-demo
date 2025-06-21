package com.liajay.demo.permission.model.entity;

import com.liajay.demo.common.model.dto.RoleCode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Stack;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @Column(name = "role_id", nullable = false)
    private Integer id;

    @Column(name = "role", nullable = false, length = 20)
    private String role;

    public Role() {
    }

    public Role(Integer id, String role) {
        this.id = id;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "role{" +
                "id=" + id +
                ", role='" + role + '\'' +
                '}';
    }

    public static Role USER(){
        return new Role(RoleCode.USER.getRoleCode(), "user");
    }

    public static Role ADMIN(){
        return new Role((RoleCode.ADMIN.getRoleCode()), "admin");
    }

    public static Role SUPER_ADMIN(){
        return new Role(RoleCode.SUPER_ADMIN.getRoleCode(), "super_admin");
    }
}
