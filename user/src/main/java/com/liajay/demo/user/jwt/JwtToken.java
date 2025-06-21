package com.liajay.demo.user.jwt;

import com.liajay.demo.common.model.dto.RoleCode;
import java.time.Duration;
import java.time.Instant;

public record JwtToken(long userId , long issuedAt, long expires) {
    public JwtToken(long userId , Instant issuedTime, Duration expireDuration) {
        this(userId , issuedTime.getEpochSecond(), issuedTime.plus((expireDuration)).getEpochSecond());
    }
}
