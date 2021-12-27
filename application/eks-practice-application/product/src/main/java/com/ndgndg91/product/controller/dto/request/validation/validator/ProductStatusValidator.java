package com.ndgndg91.product.controller.dto.request.validation.validator;


import com.ndgndg91.product.domain.ProductStatus;
import com.ndgndg91.product.controller.dto.request.validation.annotation.ValidProductStatus;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class ProductStatusValidator implements ConstraintValidator<ValidProductStatus, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (Objects.isNull(value)){
            addConstraint(context, "{CreateProductRequest.status.null}");
            return false;
        }

        try {
            ProductStatus.valueOf(value);
        } catch (IllegalArgumentException e) {
            addConstraint(context, "{CreateProductRequest.status.notSupported}");
            return false;
        }

        return true;
    }

    private void addConstraint(ConstraintValidatorContext context, String messageTemplate) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(messageTemplate).addConstraintViolation();
    }
}
