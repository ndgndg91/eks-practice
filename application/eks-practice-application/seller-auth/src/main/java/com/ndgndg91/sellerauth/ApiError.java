package com.ndgndg91.sellerauth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public final class ApiError {

    private final Body error;

    public ApiError(final String errorCode, final String errorMessage) {
        this.error = new Body(errorCode, errorMessage);
    }

    public ApiError(final String errorCode, final String errorMessage, String... additional) {
        this.error = new Body(errorCode, errorMessage + "\n" + String.join("\n", additional));
    }

    public ApiError(final ErrorCode errorCode) {
        this.error = new Body(errorCode.name(), errorCode.getMessage());
    }

    @Getter
    @RequiredArgsConstructor
    private static class Body {
        private final String code;
        private final String message;

    }
}
