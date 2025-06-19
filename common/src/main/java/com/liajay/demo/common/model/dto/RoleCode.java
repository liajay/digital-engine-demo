package com.liajay.demo.common.model.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class RoleCode {

    private final int roleCode;

    public static RoleCode SUPER_ADMIN = new RoleCode(1);
    public static RoleCode ADMIN = new RoleCode(3);
    public static RoleCode USER = new RoleCode(2);

    @JsonCreator
    public RoleCode(@JsonProperty("roleCode") int roleCode) {
        this.roleCode = roleCode;
    }

    public int getRoleCode() {
        return roleCode;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof RoleCode roleCode1)) return false;
        return roleCode == roleCode1.roleCode;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(roleCode);
    }

    public String getRoleName(){
        return switch (this.roleCode) {
            case 1 -> "SUPER_ADMIN";
            case 3 -> "ADMIN";
            default -> "USER";
        };
    }
}

