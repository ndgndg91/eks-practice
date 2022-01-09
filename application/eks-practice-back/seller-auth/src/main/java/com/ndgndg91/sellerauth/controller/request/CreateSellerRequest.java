package com.ndgndg91.sellerauth.controller.request;

import com.ndgndg91.sellerauth.domain.Seller;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Getter
@ToString
public final class CreateSellerRequest {
    @NotNull
    @Length(max = 35)
    private String identifier;
    @NotNull
    @Length(min = 10, max = 50)
    private String password;
    @NotNull
    private Boolean marketingAgreement;

    public Seller toSeller() {
        return new Seller(identifier, password, marketingAgreement);
    }
}
