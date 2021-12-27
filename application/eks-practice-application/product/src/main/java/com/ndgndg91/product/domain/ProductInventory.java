package com.ndgndg91.product.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductInventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long availableQuantity;
    private boolean tracked;

    @Setter
    @OneToOne
    @JoinColumn(name = "product_variant_id", nullable = false, updatable = false)
    private ProductVariant productVariant;

    public ProductInventory(final long availableQuantity, final boolean tracked) {
        this.availableQuantity = availableQuantity;
        this.tracked = tracked;
    }
}
