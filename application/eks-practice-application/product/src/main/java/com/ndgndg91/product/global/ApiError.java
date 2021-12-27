package com.ndgndg91.product.global;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Map;

@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiError {
    private final String message;
    private final String detail;
    private final Map<String, String> fieldErrors;

    public static ApiError of(final String message, final String detail) {
            return new ApiError(message, detail, null);
    }

    public static ApiError of(final String message, final String detail, final Map<String, String> fieldErrors) {
        return new ApiError(message, detail, fieldErrors);
    }
}
