package com.ndgndg91.product.controller.dto.request.validation.validator;


import com.ndgndg91.product.controller.dto.request.validation.annotation.ValidProductOptionNames;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.Objects;

public class ProductOptionNamesValidator implements ConstraintValidator<ValidProductOptionNames, List<String>> {
    @Override
    public boolean isValid(List<String> value, ConstraintValidatorContext context) {
        return !Objects.isNull(value);
    }
}
