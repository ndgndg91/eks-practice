package com.ndgndg91.sellerauth.domain.vo;

import com.ndgndg91.sellerauth.ErrorCode;
import com.ndgndg91.sellerauth.ServiceException;
import java.util.Arrays;

public enum Sex {
    MAN("01"),
    WOMAN("02");

    private final String code;

    Sex(String code) {
        this.code = code;
    }

    public static Sex codeOf(String code) {
        return Arrays.stream(Sex.values())
            .filter(sex -> sex.code.equals(code))
            .findAny()
            .orElseThrow(() -> new ServiceException(400, ErrorCode.ESP1002, code));
    }
}
