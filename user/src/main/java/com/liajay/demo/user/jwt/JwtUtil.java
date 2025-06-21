package com.liajay.demo.user.jwt;

import com.liajay.demo.common.exception.ErrorCode;
import com.liajay.demo.common.model.dto.RoleCode;
import com.liajay.demo.user.jwt.exception.JwtVerifyException;
import com.liajay.demo.user.jwt.exception.MissingFieldException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.Nonnull;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

@Component
public class JwtUtil {
    private final JwtProperties jwtProperties;

    public JwtUtil(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    private JwtParser parser;
    private SecretKey key;
    private Duration expire;

    @PostConstruct
    private void initializeSecretKey() {
        key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.secretKey()));
        parser = Jwts.parser().verifyWith(key).build();
        expire = Duration.ofSeconds(jwtProperties.expiration());
    }

    @Nonnull
    public String encode(@Nonnull JwtToken jwtToken){
        return Jwts.builder()
                .claim("userId", jwtToken.userId())
                .issuer(jwtProperties.issuer())
                .issuedAt(Date.from(Instant.ofEpochSecond(jwtToken.issuedAt())))
                .expiration(Date.from(Instant.ofEpochSecond(jwtToken.expires())))
                .signWith(key)
                .compact();
    }

    @Nonnull
    public String encode(long userId){
        return encode(new JwtToken(
                userId,
                Instant.now(),
                this.expire
        ));
    }

    @Nonnull
    public JwtToken parseAndVerify(@Nonnull String token,@Nonnull Instant now) throws JwtException {
        Jws<Claims> claims = null;
        try {
            claims = parser.parseSignedClaims(token);
        } catch (JwtException e) {
            throw new JwtVerifyException(null, ErrorCode.USER_TOKEN_INVALID,e.getMessage());
        }
        Instant expiration = Optional.ofNullable(claims.getPayload().getExpiration()).orElseThrow(
                () -> new MissingFieldException(null, "exp")
        ).toInstant();
        Instant issuedAt = Optional.ofNullable(claims.getPayload().getIssuedAt()).orElseThrow(
                () -> new MissingFieldException(null, "iat")
        ).toInstant();


        JwtToken jwtToken =  new JwtToken(
                Optional.ofNullable(claims.getPayload().get("userId", Number.class)).orElseThrow(
                        () -> new MissingFieldException(null, "user_id")
                ).longValue(),
                issuedAt.getEpochSecond(),
                expiration.getEpochSecond()
        );

        Instant nowSeconds = now.truncatedTo(ChronoUnit.SECONDS);

        var expireTimeLeft = Duration.between(nowSeconds,expiration);
        if (expireTimeLeft.isNegative()){
            throw new JwtVerifyException(jwtToken, ErrorCode.USER_AUTHENTICATION_FAILED, "jwt过期");
        }
        return jwtToken;
    }

    public Duration getExpire() {
        return expire;
    }
}
