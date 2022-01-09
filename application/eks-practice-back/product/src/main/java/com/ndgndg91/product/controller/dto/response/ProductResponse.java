package com.ndgndg91.product.controller.dto.response;

import com.ndgndg91.product.domain.Media;
import com.ndgndg91.product.domain.Product;
import com.ndgndg91.product.domain.ProductInventory;
import com.ndgndg91.product.domain.ProductVariant;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@ToString
public final class ProductResponse {
    private final long id;
    private final String title;
    private final String description;
    private final String seoTitle;
    private final String seoDescription;
    private final List<MediaResponse> media;
    private final List<String> optionNames;
    private final String status;
    private final List<ProductVariantResponse> variants;

    public ProductResponse(final Product product) {
        this.id = product.getId();
        this.title = product.getTitle();
        this.description = product.getDescription();
        this.seoTitle = product.getSeo().getTitle();
        this.seoDescription = product.getSeo().getDescription();
        this.media = product.getMedia().stream()
                .map(MediaResponse::new)
                .collect(Collectors.toList());
        this.optionNames = product.getOptionNames();
        this.status = product.getStatus().name();
        this.variants = product.getVariants().stream()
                .map(ProductVariantResponse::new)
                .collect(Collectors.toList());
    }

    @Getter
    @ToString
    private static final class MediaResponse {
        private final String type;
        private final String originalSource;

        MediaResponse(final Media media) {
            this.type = media.getType().name();
            this.originalSource = media.getOriginalSource();
        }
    }

    @Getter
    @ToString
    private static final class ProductVariantResponse {
        private final long variantId;
        private final String sku;
        private final BigDecimal price;
        private final List<String> optionValues;
        private final ProductInventoryResponse inventory;

        ProductVariantResponse(final ProductVariant variant) {
            this.variantId = variant.getId();
            this.sku = variant.getSku();
            this.price = variant.getPrice();
            this.optionValues = variant.getOptionValues();
            this.inventory = new ProductInventoryResponse(variant.getInventory());
        }
    }

    @Getter
    @ToString
    private static final class ProductInventoryResponse {
        private final long inventoryId;
        private final long availableQuantity;
        private final boolean tracked;

        ProductInventoryResponse(final ProductInventory inventory) {
            this.inventoryId = inventory.getId();
            this.availableQuantity = inventory.getAvailableQuantity();
            this.tracked = inventory.isTracked();
        }
    }
}
