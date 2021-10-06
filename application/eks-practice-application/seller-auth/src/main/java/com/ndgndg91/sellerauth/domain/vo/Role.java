package com.ndgndg91.sellerauth.domain.vo;

import java.util.List;
import lombok.Getter;

@Getter
public enum Role {
    OWNER("ROLE_OWNER", Authority.owner());

    private final String value;
    private final List<Authority> authorities;

    Role(String value, List<Authority> authorities) {
        this.value = value;
        this.authorities = authorities;
    }
}
