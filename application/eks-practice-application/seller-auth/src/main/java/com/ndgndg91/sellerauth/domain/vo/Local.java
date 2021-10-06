package com.ndgndg91.sellerauth.domain.vo;

import com.ndgndg91.sellerauth.ErrorCode;
import com.ndgndg91.sellerauth.ServiceException;
import java.util.Arrays;

public enum Local {
    DOMESTIC("01"),
    FOREIGN("02");

    private final String code;

    Local(String code) {
        this.code = code;
    }

    public static Local codeOf(String code) {
        return Arrays.stream(Local.values())
            .filter(local -> local.code.equals(code))
            .findAny()
            .orElseThrow(() -> new ServiceException(400, ErrorCode.ESP1001));
    }

    public static boolean isForeign(String localCode) {
        return FOREIGN.code.equals(localCode);
    }
}