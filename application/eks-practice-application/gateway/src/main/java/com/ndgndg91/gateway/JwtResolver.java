package com.ndgndg91.gateway;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.Objects;

@Slf4j
public class JwtResolver {

    private final String key;

    public JwtResolver(String key) {
        this.key = key;
        log.info(key);
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
            log.info("token is expired : {}", jwt);
            return expired;
        } catch (JwtException e) {
            return true;
        }
    }
}
