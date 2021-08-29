package com.ndgndg91.sellerauth.controller.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public final class LoginResponse {
    private final String accessToken;
    private final String refreshToken;
}
