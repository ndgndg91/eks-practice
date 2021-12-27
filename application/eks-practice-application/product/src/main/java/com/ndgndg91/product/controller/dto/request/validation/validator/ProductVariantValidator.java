package com.ndgndg91.product.controller.dto.request.validation.validator;


import com.ndgndg91.product.controller.dto.request.ProductVariantRequest;
import com.ndgndg91.product.controller.dto.request.validation.annotation.ValidProductVariants;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.Objects;

public class ProductVariantValidator implements ConstraintValidator<ValidProductVariants, List<ProductVariantRequest>> {
    @Override
    public boolean isValid(List<ProductVariantRequest> value, ConstraintValidatorContext context) {
        if (Objects.isNull(value)) {
            addConstraint(context, "{CreateProductRequest.variants.null}");
            return false;
        }

        if (value.isEmpty()) {
            addConstraint(context, "{CreateProductRequest.variants.empty}");
            return false;
        }

        if (value.size() > 100) {
            addConstraint(context, "{CreateProductRequest.variants.overSize}");
            return false;
        }

        for (var variant : value) {
            if (Objects.isNull(variant.getSku())) {
                addConstraint(context, "{CreateProductRequest.variants.sku.null}");
                return false;
            }

            if (Objects.isNull(variant.getInventory())) {
                addConstraint(context, "{CreateProductRequest.variants.inventory.null}");
                return false;
            }

            if (Objects.isNull(variant.getInventory().getAvailableQuantity())) {
                addConstraint(context, "{CreateProductRequest.variants.inventory.availableQuantity.null}");
                return false;
            }

            if (variant.getInventory().getAvailableQuantity() < 0) {
                addConstraint(context, "{CreateProductRequest.variants.inventory.availableQuantity.underZero}");
                return false;
            }

            if (Objects.isNull(variant.getInventory().getTracked())) {
                addConstraint(context, "{CreateProductRequest.variants.inventory.tracked.null}");
                return false;
            }

            if (Objects.isNull(variant.getOptionValues())) {
                addConstraint(context, "{CreateProductRequest.variants.optionValues.null}");
                return false;
            }

            if (variant.getOptionValues().size() > 3) {
                addConstraint(context, "{CreateProductRequest.variants.optionValues.exceedSize}");
                return false;
            }

            if (Objects.isNull(variant.getPrice())) {
                addConstraint(context, "{CreateProductRequest.variants.price.null}");
                return false;
            }

            if (variant.getPrice().longValue() < 0) {
                addConstraint(context, "{CreateProductRequest.variants.underZero}");
                return false;
            }
        }

        return true;
    }

    private void addConstraint(ConstraintValidatorContext context, String messageTemplate) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(messageTemplate).addConstraintViolation();
    }
}
