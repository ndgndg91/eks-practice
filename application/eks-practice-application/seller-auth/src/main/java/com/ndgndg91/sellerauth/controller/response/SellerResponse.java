package com.ndgndg91.sellerauth.controller.response;

import com.ndgndg91.sellerauth.domain.Seller;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public final class SellerResponse {
    private final long id;
    private final String identifier;
    private final String role;

    public SellerResponse(Seller seller) {
        this.id = seller.getId();
        this.identifier = seller.getIdentifier();
        this.role = seller.getRole().name();
    }

}
