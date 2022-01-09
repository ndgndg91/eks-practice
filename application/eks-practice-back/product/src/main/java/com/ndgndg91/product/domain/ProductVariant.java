package com.ndgndg91.product.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductVariant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sku;
    private BigDecimal price;

    @Convert(converter = OptionValueConverter.class)
    @Column(name = "option_values", columnDefinition = "json")
    private final List<String> optionValues = new ArrayList<>();

    @Setter
    @OneToOne(mappedBy = "productVariant", cascade = CascadeType.ALL)
    private ProductInventory inventory;

    @Setter
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false, updatable = false)
    private Product product;

    public ProductVariant(final String sku, final BigDecimal price, final List<String> optionValues) {
        this.sku = sku;
        this.price = price;
        this.optionValues.addAll(optionValues);
    }
}