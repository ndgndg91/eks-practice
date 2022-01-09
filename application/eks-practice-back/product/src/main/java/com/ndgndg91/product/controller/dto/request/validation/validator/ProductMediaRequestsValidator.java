package com.ndgndg91.product.controller.dto.request.validation.validator;

import com.ndgndg91.product.domain.MediaContentType;
import com.ndgndg91.product.controller.dto.request.ProductMediaRequest;
import com.ndgndg91.product.controller.dto.request.validation.annotation.ValidProductMediaRequests;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.Objects;

@Slf4j
public class ProductMediaRequestsValidator implements ConstraintValidator<ValidProductMediaRequests, List<ProductMediaRequest>> {

    @Override
    public boolean isValid(List<ProductMediaRequest> value, ConstraintValidatorContext context) {
        if (Objects.isNull(value)) {
            addConstraint(context, "{CreateProductRequest.media.null}");
            return false;
        }

        for (var media : value) {
            String type = media.getType();
            if (Objects.isNull(type)) {
                addConstraint(context, "{CreateProductRequest.media.type.null}");
                return false;
            }

            try {
                MediaContentType.valueOf(type);
            } catch (IllegalArgumentException e) {
                addConstraint(context, "{CreateProductRequest.media.type.notSupported}");
                return false;
            }

            if (Objects.isNull(media.getOriginalSource())) {
                addConstraint(context, "{CreateProductRequest.media.originalSource.null}");
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
