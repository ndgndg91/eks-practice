package com.ndgndg91.sellerauth.domain.vo;

import java.util.List;

public enum Authority {
    READ_DESIGN,
    UPDATE_DESIGN,
    PUBLISH_DESIGN,
    UPDATE_STORE;

    public static List<Authority> owner(){
        return List.of(READ_DESIGN, UPDATE_DESIGN, PUBLISH_DESIGN, UPDATE_STORE);
    }
}
