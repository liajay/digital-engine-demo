package com.liajay.demo.user.model.entity;

import com.liajay.demo.common.model.dto.RoleCode;
import org.springframework.security.core.GrantedAuthority;

public class GrantedRole extends RoleCode implements GrantedAuthority {
    public GrantedRole(int roleCode) {
        super(roleCode);
    }

    public GrantedRole(RoleCode role){
        super(role.getRoleCode());
    }
    @Override
    public String getAuthority() {
        return getRoleName();
    }
}
