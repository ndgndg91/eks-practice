package com.ndgndg91.product.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @Embedded
    @AttributeOverride(name = "title", column = @Column(name = "seo_title"))
    @AttributeOverride(name = "description", column = @Column(name = "seo_description"))
    private ProductSEO seo;

    @Convert(converter = ProductMediaConverter.class)
    @Column(name = "media", columnDefinition = "json")
    private final List<Media> media = new ArrayList<>();

    @Convert(converter = OptionNameConverter.class)
    @Column(name = "option_names", columnDefinition = "json")
    private final List<String> optionNames = new ArrayList<>();

    @Enumerated(value = EnumType.STRING)
    private ProductStatus status;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private final List<ProductVariant> variants = new ArrayList<>();

    @Builder
    private Product(final String title, final String description, final String seoTitle, final String seoDescription,
                    final List<Media> media, final List<String> optionNames, final ProductStatus status, final List<ProductVariant> variants) {
        this.title = title;
        this.description = description;
        this.seo = new ProductSEO(seoTitle, seoDescription);
        this.media.addAll(media);
        this.optionNames.addAll(optionNames);
        this.status = status;
        for (var variant : variants) {
            variant.setProduct(this);
        }
        this.variants.addAll(variants);

    }
}