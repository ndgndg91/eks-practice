package com.ndgndg91.product.controller.dto.request.validation.annotation;


import com.ndgndg91.product.controller.dto.request.validation.validator.ProductOptionNamesValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {ProductOptionNamesValidator.class })
public @interface ValidProductOptionNames {
    String message() default "{CreateProductRequest.optionNames.null}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}