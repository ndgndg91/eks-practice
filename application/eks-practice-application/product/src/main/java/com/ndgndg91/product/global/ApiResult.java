package com.ndgndg91.product.global;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ApiResult<T> {
    private T data;

    public static <T> ApiResult<T> ok(final T data) {
        ApiResult<T> result = new ApiResult<>();
        result.data = data;
        return result;
    }
}
