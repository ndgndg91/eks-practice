package com.ndgndg91.product.controller.dto.request;

import com.ndgndg91.product.domain.Media;
import com.ndgndg91.product.domain.MediaContentType;
import com.ndgndg91.product.domain.Product;
import com.ndgndg91.product.domain.ProductStatus;
import com.ndgndg91.product.controller.dto.request.validation.annotation.ValidProductMediaRequests;
import com.ndgndg91.product.controller.dto.request.validation.annotation.ValidProductOptionNames;
import com.ndgndg91.product.controller.dto.request.validation.annotation.ValidProductStatus;
import com.ndgndg91.product.controller.dto.request.validation.annotation.ValidProductVariants;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@ToString
public class CreateProductRequest {
    @Length(max = 255, message = "{CreateProductRequest.title.maxLength}")
    @NotNull(message = "{CreateProductRequest.title.null}")
    private String title;
    @Length(max = 255, message = "{CreateProductRequest.description.maxLength}")
    @NotNull(message = "{CreateProductRequest.description.null}")
    private String description;
    @Length(max = 255, message = "{CreateProductRequest.seoTitle.maxLength}")
    @NotNull(message = "{CreateProductRequest.seoTitle.null}")
    private String seoTitle;
    @Length(max = 255, message = "{CreateProductRequest.seoDescription.maxLength}")
    @NotNull(message = "{CreateProductRequest.seoDescription.null}")
    private String seoDescription;
    @ValidProductMediaRequests
    private List<ProductMediaRequest> media;
    @ValidProductOptionNames
    private List<String> optionNames;
    @ValidProductStatus
    private String status;
    @ValidProductVariants
    private List<ProductVariantRequest> variants;


    public Product toProduct() {
        final var productMedia = this.media.stream()
                .map(m -> new Media(MediaContentType.valueOf(m.getType()), m.getOriginalSource()))
                .collect(Collectors.toList());
        final var productVariants = this.variants.stream()
                .map(ProductVariantRequest::toProductVariant)
                .collect(Collectors.toList());
        return Product.builder()
                .title(title)
                .description(description)
                .seoTitle(seoTitle)
                .seoDescription(seoDescription)
                .media(productMedia)
                .optionNames(optionNames)
                .status(ProductStatus.valueOf(status))
                .variants(productVariants)
                .build();
    }
}
