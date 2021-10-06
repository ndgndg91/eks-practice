package com.ndgndg91.sellerauth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
public class ApiResult<T> {

    private final T data;

    public static <T> ApiResult<T> body(T data) {
        return new ApiResult<>(data);
    }
}
