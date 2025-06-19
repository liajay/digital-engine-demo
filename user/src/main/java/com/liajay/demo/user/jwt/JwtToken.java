package com.liajay.demo.user.jwt;

import com.liajay.demo.common.model.dto.RoleCode;
import java.time.Duration;
import java.time.Instant;

public record JwtToken(long userId , int role_id, long issuedAt, long expires) {
    public JwtToken(long userId , RoleCode role, Instant issuedTime, Duration expireDuration) {
        this(userId , role.getRoleCode(), issuedTime.getEpochSecond(), issuedTime.plus((expireDuration)).getEpochSecond());
    }
}
