package com.ndgndg91.product.controller.dto.request;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public final class ProductMediaRequest {
    private String type;
    private String originalSource;
}
