package com.ndgndg91.sellerauth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class JwtResolver {
    public static final String IS_ACCESS = "access";

    private final String key;
    private final String issuer;
    private final long accessTokenExpirationMinutes;
    private final long refreshTokenExpirationMinutes;
    private final Map<String, Object> headers;

    public JwtResolver(String key, long accessTokenExpirationMinutes, String issuer, long refreshTokenExpirationMinutes) {
        this.key = key;
        this.issuer = issuer;
        log.info(key);
        this.accessTokenExpirationMinutes = accessTokenExpirationMinutes;
        this.refreshTokenExpirationMinutes = refreshTokenExpirationMinutes;
        this.headers = new HashMap<>();
        this.headers.put("typ", "JWT");
        this.headers.put("alg", SignatureAlgorithm.HS256.name());
    }

    public String issueAccessToken(
            final long id,
            final Long storeId,
            final String userName
    ) {
        Map<String, Object> claims = accessClaims(id, storeId, userName);
        return issue(claims, accessTokenExpirationMinutes);
    }

    public String issueRefreshToken(
            final long id
    ) {
        Map<String, Object> claims = refreshClaims(id);
        return issue(claims, refreshTokenExpirationMinutes);
    }

    private Map<String, Object> accessClaims(
            final long id,
            final Long storeId,
            final String userName
    ) {
        Map<String, Object> claims = new HashMap<>();

        claims.put("sellerId", id);
        claims.put("storeId", storeId);
        claims.put("userName", userName);
        claims.put(IS_ACCESS, true);

        return claims;
    }

    private Map<String, Object> refreshClaims(
            final long id
    ) {
        Map<String, Object> claims = new HashMap<>();

        claims.put("sellerId", id);
        claims.put(IS_ACCESS, false);
        return claims;
    }

    private String issue(
            final Map<String, Object> claims,
            final long expirationMinutes
    ) {
        return Jwts.builder()
                .setHeader(headers)
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setIssuer(issuer)
                .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(expirationMinutes).toInstant()))
                .signWith(SignatureAlgorithm.HS256, key.getBytes())
                .compact();
    }

    public Claims getAllClaimsFromToken(final String jwt) {
        return Jwts.parser()
                .setSigningKey(key.getBytes())
                .parseClaimsJws(jwt)
                .getBody();
    }

    public boolean isInvalid(final String jwt) {
        if (Objects.isNull(jwt)) return false;
        try {
            boolean expired = getAllClaimsFromToken(jwt).getExpiration().before(new Date());
            return !expired;
        } catch (JwtException e) {
            return true;
        }
    }
}
