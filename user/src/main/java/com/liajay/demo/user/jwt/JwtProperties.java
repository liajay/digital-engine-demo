package com.liajay.demo.user.jwt;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
public record JwtProperties(
        String secretKey,
        long expiration,
        String issuer
) {
}
