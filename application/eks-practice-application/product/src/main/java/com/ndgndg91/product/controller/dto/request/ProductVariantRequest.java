package com.ndgndg91.product.controller.dto.request;

import com.ndgndg91.product.domain.ProductVariant;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Getter
@ToString
public final class ProductVariantRequest {
    private String sku;
    private BigDecimal price;
    private List<String> optionValues;
    private ProductInventoryRequest inventory;

    public ProductVariant toProductVariant() {
        final var productInventory = inventory.toProductInventory();
        final var productVariant = new ProductVariant(sku, price, optionValues);
        productVariant.setInventory(productInventory);
        productInventory.setProductVariant(productVariant);
        return productVariant;
    }
}
