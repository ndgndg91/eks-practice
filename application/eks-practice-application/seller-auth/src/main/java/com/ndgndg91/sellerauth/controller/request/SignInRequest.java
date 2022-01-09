package com.ndgndg91.sellerauth.controller.request;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public final class SignInRequest {
    private String identifier;
    private String password;
}
