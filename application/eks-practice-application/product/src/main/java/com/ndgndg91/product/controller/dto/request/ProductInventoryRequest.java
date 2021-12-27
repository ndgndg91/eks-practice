package com.ndgndg91.product.controller.dto.request;

import com.ndgndg91.product.domain.ProductInventory;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ProductInventoryRequest {
    private Long availableQuantity;
    private Boolean tracked;

    protected ProductInventory toProductInventory() {
        return new ProductInventory(availableQuantity, tracked);
    }
}
