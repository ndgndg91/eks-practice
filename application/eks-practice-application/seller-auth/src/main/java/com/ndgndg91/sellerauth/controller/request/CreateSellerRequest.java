package com.ndgndg91.sellerauth.controller.request;

import com.ndgndg91.sellerauth.domain.Seller;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public final class CreateSellerRequest {
    private String identifier;
    private String password;
    private boolean marketingAgreement;

    public Seller toSeller() {
        return new Seller(identifier, password, marketingAgreement);
    }
}
