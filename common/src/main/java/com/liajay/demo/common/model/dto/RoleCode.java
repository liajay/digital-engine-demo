package com.liajay.demo.common.model.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.management.relation.Role;
import java.io.IOException;
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
}

